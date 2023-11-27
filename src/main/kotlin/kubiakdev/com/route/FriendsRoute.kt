package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authorization.firebase.util.AuthenticationConst.FIREBASE_AUTH_CONFIGURATION_NAME
import kubiakdev.com.app.authorization.firebase.util.FirebaseUser
import kubiakdev.com.data.database.dao.FriendsDao
import kubiakdev.com.route.model.friend.FriendsRouteModel
import kubiakdev.com.util.mapper.toEntityModel
import kubiakdev.com.util.mapper.toRouteModel

fun Route.friendsRoutes() {
    val db = FriendsDao()

    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        route("/friends/{ownerId}") {
            get {
                call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                val ownerId = call.parameters["ownerId"]
                if (ownerId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong ownerId param")
                    return@get
                }

                try {
                    val friends = db.loadAll(ownerId)?.toRouteModel()
                    if (friends != null) {
                        call.respond(HttpStatusCode.OK, friends)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route("/friends") {
            post {
                call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val friends = try {
                    call.receive<FriendsRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong friends body")
                    return@post
                }

                try {
                    val friendsId = db.create(friends.toEntityModel())
                    if (friendsId != null) {
                        call.respond(HttpStatusCode.Created)
                    } else {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route("/friends") {
            patch {
                call.principal<FirebaseUser>() ?: return@patch call.respond(HttpStatusCode.Unauthorized)

                val friends = try {
                    call.receive<FriendsRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong friends body")
                    return@patch
                }

                try {
                    val updated = db.update(friends.toEntityModel())
                    if (updated) {
                        call.respond(HttpStatusCode.NoContent)
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Object not updated successfully")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }
    }
}
