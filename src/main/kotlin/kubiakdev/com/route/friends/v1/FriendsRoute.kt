package kubiakdev.com.route.friends.v1

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.FIREBASE_AUTH_CONFIGURATION_NAME
import kubiakdev.com.app.authentication.firebase.util.FirebaseUser
import kubiakdev.com.app.friends.AddFriendUseCase
import kubiakdev.com.app.friends.FindFriendUseCase
import kubiakdev.com.app.friends.LoadFriendsUseCase
import kubiakdev.com.app.friends.RemoveFriendUseCase
import kubiakdev.com.route.friends.v1.model.FriendRouteModel
import kubiakdev.com.route.friends.v1.model.toDomainModel
import kubiakdev.com.route.friends.v1.model.toRouteModel
import org.koin.ktor.ext.inject

fun Route.friendsRoutes() {
    val loadFriendsUseCase by inject<LoadFriendsUseCase>()
    val findFriendUseCase by inject<FindFriendUseCase>()
    val addFriendUseCase by inject<AddFriendUseCase>()
    val removeFriendUseCase by inject<RemoveFriendUseCase>()

    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        route("/v1/friends") {
            get {
                val principal = call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                try {
                    val friends = loadFriendsUseCase.loadFriends(userId = principal.userId)
                    if (friends != null) {
                        call.respond(HttpStatusCode.OK, friends)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }

            patch {
                val principal = call.principal<FirebaseUser>() ?: return@patch call.respond(HttpStatusCode.Unauthorized)

                // todo add validation that only friends owner can add friends list, get data from principal
                val friend = try {
                    call.receive<FriendRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong friends body")
                    return@patch
                }

                try {
                    addFriendUseCase.addFriend(userId = principal.userId, friend.toDomainModel())
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route("/v1/friend") {
            get {
                call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong email param")
                    return@get
                }

                // todo add validation that only friends owner can add friends list, get data from principal
                try {
                    val friend = findFriendUseCase.findUser(email)?.toRouteModel()
                    if (friend != null) {
                        call.respond(HttpStatusCode.OK, friend)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }


            delete {
                val principal =
                    call.principal<FirebaseUser>() ?: return@delete call.respond(HttpStatusCode.Unauthorized)

                // todo add validation that only friends owner can add friends list, get data from principal
                val friend = try {
                    call.receive<FriendRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong friends body")
                    return@delete
                }

                try {
                    removeFriendUseCase.removeFriend(userId = principal.userId, friend.toDomainModel())
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }
    }
}
