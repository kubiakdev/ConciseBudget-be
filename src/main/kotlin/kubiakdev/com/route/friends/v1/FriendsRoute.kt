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
import kubiakdev.com.route.util.RouteConst
import org.koin.ktor.ext.inject

fun Route.friendsRoutes() {
    val loadFriendsUseCase by inject<LoadFriendsUseCase>()
    val findFriendUseCase by inject<FindFriendUseCase>()
    val addFriendUseCase by inject<AddFriendUseCase>()
    val removeFriendUseCase by inject<RemoveFriendUseCase>()

    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        route(RouteConst.ROUTE_V1_FRIENDS) {
            get {
                val principal = call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                try {
                    val friends = loadFriendsUseCase.loadFriends(userId = principal.authId)
                    if (friends != null) {
                        call.respond(HttpStatusCode.OK, friends.toRouteModel())
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route(RouteConst.ROUTE_V1_FRIEND) {
            get {
                call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong email param")
                    return@get
                }

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

            post {
                val principal = call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val friend = try {
                    call.receive<FriendRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong friends body")
                    return@post
                }

                try {
                    addFriendUseCase.addFriend(userId = principal.authId, friend.toDomainModel())
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }

            delete {
                val principal =
                    call.principal<FirebaseUser>() ?: return@delete call.respond(HttpStatusCode.Unauthorized)

                val friend = try {
                    call.receive<FriendRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong friends body")
                    return@delete
                }

                try {
                    removeFriendUseCase.removeFriend(userId = principal.authId, friend.toDomainModel())
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }
    }
}
