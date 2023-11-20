package kubiakdev.com.plugin

import io.ktor.server.application.*
import io.ktor.server.routing.*
import kubiakdev.com.route.authorizationRoutes
import kubiakdev.com.route.friendsRoutes
import kubiakdev.com.route.transactionRoutes
import kubiakdev.com.route.userRoutes

fun Application.configureRouting() {
    routing {
        authorizationRoutes()
        userRoutes()
        friendsRoutes()
        transactionRoutes()
    }
}
