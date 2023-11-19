package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.data.database.getUserById

fun Route.userRoutes() {
    route("/user/{id}") {
        get {
            val id = call.parameters["id"]!!
            val user = getUserById(id)
            user?.let {
                call.respond(
                    HttpStatusCode.OK,
                    "found",
                )
            } ?: call.respond(
                HttpStatusCode.NotFound,
                "not found",
            )
        }
    }
}