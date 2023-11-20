package kubiakdev.com

import io.ktor.server.application.*
import kubiakdev.com.plugin.*
import kubiakdev.com.plugin.temp.FirebaseAdmin
import kubiakdev.com.plugin.temp.configureFirebaseAuth

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    FirebaseAdmin.init()
    configureFirebaseAuth()
    configureSerialization()
    configureRouting()
}
