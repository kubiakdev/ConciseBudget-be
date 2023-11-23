package kubiakdev.com.plugin

import io.ktor.server.application.*
import io.ktor.server.routing.*
import kubiakdev.com.route.*

fun Application.configureRouting() {
    routing {
        rootRoute()
        authenticationRoutes()
        userRoutes()
        friendsRoutes()
        transactionRoutes()
    }
}
