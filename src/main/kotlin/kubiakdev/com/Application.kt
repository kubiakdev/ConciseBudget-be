package kubiakdev.com

import io.ktor.server.application.*
import kubiakdev.com.plugin.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
