package kubiakdev.com

import io.ktor.server.application.*
import kubiakdev.com.plugin.FirebaseAdmin
import kubiakdev.com.plugin.configureAuthentication
import kubiakdev.com.plugin.configureRouting
import kubiakdev.com.plugin.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    FirebaseAdmin.init()
    configureAuthentication()
    configureSerialization()
    configureRouting()
}
