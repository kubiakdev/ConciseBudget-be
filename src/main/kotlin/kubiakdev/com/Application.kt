package kubiakdev.com

import io.ktor.server.application.*
import kubiakdev.com.plugins.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
