package kubiakdev.com.route

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes() {
    route("/user/{id}") {
        get {
            val id = call.parameters["id"]
            call.respondText("test")
        }
    }
}