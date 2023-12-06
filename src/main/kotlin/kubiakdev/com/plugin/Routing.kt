package kubiakdev.com.plugin

import io.ktor.server.application.*
import io.ktor.server.routing.*
import kubiakdev.com.route.friends.v1.friendsRoutes
import kubiakdev.com.route.transaction.v1.transactionRoutes
import kubiakdev.com.route.user.v1.userRoutes

fun Application.configureRouting() {
    routing {
        userRoutes()
        friendsRoutes()
        transactionRoutes()
    }
}
