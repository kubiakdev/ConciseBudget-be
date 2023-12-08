package kubiakdev.com.app.authentication.firebase.util

import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

class TokenParser {

    fun parseToken(call: ApplicationCall): HttpAuthHeader? = try {
        call.request.parseAuthorizationHeader()
    } catch (ex: IllegalArgumentException) {
        println("failed to parse token")
        null
    }
}