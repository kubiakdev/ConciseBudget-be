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
            val id = call.parameters["id"]!!
            val user = db.getById(id)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

    route("/user/{email}") {
        get {
            val email = call.parameters["email"]!!
            val user = db.getByEmail(email)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

    route("/user/{authUid}") {
        get {
            val authUid = call.parameters["authUid"]!!
            val user = db.getByAuthUid(authUid)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

    route("/user") {
        post {
            val user = call.receive<User>()
            val userId = db.addUser(user)
            if (userId != null) {
                call.respond(HttpStatusCode.Created)
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }

    route("/user/{id}") {
        delete {
            val id = call.parameters["id"]!!
            runCatching { db.removeById(id) }.fold(
                onSuccess = { call.respond(HttpStatusCode.NoContent) },
                onFailure = { call.respond(HttpStatusCode.InternalServerError) }
            )
        }
    }
}