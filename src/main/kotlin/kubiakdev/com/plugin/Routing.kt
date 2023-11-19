package kubiakdev.com.plugin

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.route.userRoutes

fun Application.configureRouting() {
    routing {
        userRoutes()
    }
}
