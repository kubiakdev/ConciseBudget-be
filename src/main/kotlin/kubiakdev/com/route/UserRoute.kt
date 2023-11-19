package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.data.database.getByEmail
import kubiakdev.com.data.database.getById

fun Route.userRoutes() {
    route("/user/{id}") {
        get {
            val id = call.parameters["id"]!!
            val user = getById(id)
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
            val user = getByEmail(email)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}