package kubiakdev.com.app.authentication.firebase.util

import com.google.firebase.auth.FirebaseToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.FIREBASE_JWT_AUTH_KEY

class FirebaseAuthProvider(
    private val tokenParser: TokenParser,
    private val tokenVerifier: TokenVerifier,
    config: FirebaseConfig,
) : AuthenticationProvider(config) {

    private lateinit var principalCreationMethod: AuthenticationFunction<FirebaseToken>

    fun setPrincipalCreationMethod(method: suspend ApplicationCall.(FirebaseToken) -> FirebaseUser?) {
        principalCreationMethod = method
    }

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        val token = tokenParser.parseToken(context.call)

        if (token == null) {
            context.call.respond(HttpStatusCode.Unauthorized)
            return
        }

        try {
            val principalUser = tokenVerifier.verifyFirebaseIdToken(
                call = context.call,
                authHeader = token,
                principalCreationMethod = principalCreationMethod,
            )

            if (principalUser != null) {
                context.principal(principalUser)
            }
        } catch (cause: Throwable) {
            val message = cause.message ?: cause.javaClass.simpleName
            context.error(FIREBASE_JWT_AUTH_KEY, AuthenticationFailedCause.Error(message))
        }
    }
}