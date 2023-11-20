package kubiakdev.com

import io.ktor.server.application.*
import kubiakdev.com.plugin.*
import kubiakdev.com.app.authorization.firebase.FirebaseAppInitializer
import kubiakdev.com.app.authorization.firebase.configureFirebaseAuth

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    FirebaseAppInitializer.init()
    configureFirebaseAuth()
    configureSerialization()
    configureRouting()
}
