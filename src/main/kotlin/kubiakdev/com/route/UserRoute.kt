package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.data.database.dao.UserDao
import kubiakdev.com.data.database.model.user.User

fun Route.userRoutes() {
    val db = UserDao()

    route("/user/{id}") {
        get {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Wrong id param")
                return@get
            }

            try {
                val user = db.getById(id)
                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.toString())
            }
        }
    }

    route("/user/{email}") {
        get {
            val email = call.parameters["email"]
            if (email == null) {
                call.respond(HttpStatusCode.BadRequest, "Wrong email param")
                return@get
            }

            try {
                val user = db.getByEmail(email)
                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.toString())
            }
        }
    }

    route("/user/{authUid}") {
        get {
            val authUid = call.parameters["authUid"]
            if (authUid == null) {
                call.respond(HttpStatusCode.BadRequest, "Wrong authUid param")
                return@get
            }

            try {
                val user = db.getByAuthUid(authUid)
                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.toString())
            }
        }
    }

    route("/user") {
        post {
            val user = try {
                call.receive<User>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Wrong user body")
                return@post
            }

            try {
                val userId = db.addUser(user)
                if (userId != null) {
                    call.respond(HttpStatusCode.Created)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.toString())
            }
        }
    }

    route("/user/{id}") {
        delete {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Wrong id param")
                return@delete
            }

            try {
                val removed = db.removeById(id)
                if (removed) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Object not removed successfully")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.toString())
            }
        }
    }
}