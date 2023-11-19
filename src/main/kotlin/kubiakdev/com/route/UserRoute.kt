package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.data.database.UserDatabase
import kubiakdev.com.data.model.user.User

fun Route.userRoutes() {
    val db = UserDatabase()

    route("/user/{id}") {
        get {
            val id = try {
                call.parameters["id"]!!
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            runCatching { db.getById(id) }.fold(
                onSuccess = { user ->
                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                },
                onFailure = { call.respond(HttpStatusCode.InternalServerError) }
            )
        }
    }

    route("/user/{email}") {
        get {
            val email = try {
                call.parameters["email"]!!
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            runCatching { db.getByEmail(email) }.fold(
                onSuccess = { user ->
                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                },
                onFailure = { call.respond(HttpStatusCode.InternalServerError) }
            )
        }
    }

    route("/user/{authUid}") {
        get {
            val authUid = try {
                call.parameters["authUid"]!!
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            runCatching { db.getByAuthUid(authUid) }.fold(
                onSuccess = { user ->
                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                },
                onFailure = { call.respond(HttpStatusCode.InternalServerError) }
            )
        }
    }

    route("/user") {
        post {
            val user = try {
                call.receive<User>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            runCatching { db.addUser(user) }.fold(
                onSuccess = { userId ->
                    if (userId != null) {
                        call.respond(HttpStatusCode.Created)
                    } else {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                },
                onFailure = { call.respond(HttpStatusCode.InternalServerError) }
            )
        }
    }

    route("/user/{id}") {
        delete {
            val id = try {
                call.parameters["id"]!!
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            runCatching { db.removeById(id) }.fold(
                onSuccess = { call.respond(HttpStatusCode.NoContent) },
                onFailure = { call.respond(HttpStatusCode.InternalServerError) }
            )
        }
    }
}