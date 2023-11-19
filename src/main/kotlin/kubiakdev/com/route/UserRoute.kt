package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.data.database.getByEmail

fun Route.userRoutes() {
    route("/user/{email}") {
        get {
            val email = call.parameters["email"]!!
            val user = getByEmail(email)
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