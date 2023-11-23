package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authorization.firebase.FIREBASE_AUTH
import kubiakdev.com.app.authorization.firebase.FirebaseUser
import kubiakdev.com.data.database.dao.FriendsDao
import kubiakdev.com.data.database.model.friend.FriendsEntity

fun Route.friendsRoutes() {
    val db = FriendsDao()

    authenticate(FIREBASE_AUTH) {
        route("/friends/{ownerId}") {
            get {
                call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                val ownerId = call.parameters["ownerId"]
                if (ownerId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong ownerId param")
                    return@get
                }

                try {
                    val friends = db.loadAll(ownerId)
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
                    call.receive<FriendsEntity>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong friends body")
                    return@post
                }

                try {
                    val friendsId = db.create(friends)
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
                    call.receive<FriendsEntity>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong friends body")
                    return@patch
                }

                try {
                    val updated = db.update(friends)
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
