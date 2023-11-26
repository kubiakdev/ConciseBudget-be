package kubiakdev.com.app.authorization.firebase.util

import com.google.firebase.auth.FirebaseToken
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*

class FirebaseConfig(name: String?) : AuthenticationProvider.Config(name) {

    var firebaseAuthenticationFunction: AuthenticationFunction<FirebaseToken> = {
        throw NotImplementedError(
            "Firebase auth validate function is not specified, use firebase { validate { ... } } to fix this"
        )
    }
        private set

    fun validate(validate: suspend ApplicationCall.(FirebaseToken) -> FirebaseUser?) {
        firebaseAuthenticationFunction = validate
    }

    fun parseToken(call: ApplicationCall): HttpAuthHeader? = call.request.parseAuthorizationHeaderOrNull()

    private fun ApplicationRequest.parseAuthorizationHeaderOrNull(): HttpAuthHeader? = try {
        parseAuthorizationHeader()
    } catch (ex: IllegalArgumentException) {
        println("failed to parse token")
        null
    }
}