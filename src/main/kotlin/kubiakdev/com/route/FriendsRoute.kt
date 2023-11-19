package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.data.database.FriendsDatabase
import kubiakdev.com.data.model.friend.Friends

fun Route.friendsRoutes() {
    val db = FriendsDatabase()

    route("/friends/{ownerId}") {
        get {
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
            val friends = try {
                call.receive<Friends>()
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
            val friends = try {
                call.receive<Friends>()
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
