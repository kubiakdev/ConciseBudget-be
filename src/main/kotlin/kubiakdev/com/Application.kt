package kubiakdev.com

import io.ktor.server.application.*
import kubiakdev.com.app.authentication.firebase.configureAuthentication
import kubiakdev.com.plugin.configureDependencyInjection
import kubiakdev.com.plugin.configureRouting
import kubiakdev.com.plugin.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDependencyInjection()
    configureAuthentication()
    configureSerialization()
    configureRouting()
}
