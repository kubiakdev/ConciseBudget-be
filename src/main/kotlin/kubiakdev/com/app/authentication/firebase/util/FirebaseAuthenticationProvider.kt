package kubiakdev.com.app.authentication.firebase.util

import com.google.firebase.auth.FirebaseToken
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.AUTH_SCHEME
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.FIREBASE_AUTH_CONFIGURATION_NAME
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.FIREBASE_JWT_AUTH_KEY

class FirebaseAuthProvider(config: FirebaseConfig) : AuthenticationProvider(config) {

    private val tokenParser = TokenParser()
    private val tokenVerifier = TokenVerifier()

    private lateinit var principalCreationMethod: AuthenticationFunction<FirebaseToken>

    fun setPrincipalCreationMethod(method: suspend ApplicationCall.(FirebaseToken) -> FirebaseUser?) {
        principalCreationMethod = method
    }

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        val token = tokenParser.parseToken(context.call)

        if (token == null) {
            requestForAuthenticateOnNullToken(context)
            return
        }

        try {
            val principalUser = tokenVerifier.verifyFirebaseIdToken(
                call = context.call,
                authHeader = token,
                principalCreationMethod = principalCreationMethod
            )

            if (principalUser != null) {
                context.principal(principalUser)
            }
        } catch (cause: Throwable) {
            val message = cause.message ?: cause.javaClass.simpleName
            context.error(FIREBASE_JWT_AUTH_KEY, AuthenticationFailedCause.Error(message))
        }
    }

    // todo verify that if we can remove it
    private fun requestForAuthenticateOnNullToken(context: AuthenticationContext) {
        context.challenge(
            key = FIREBASE_JWT_AUTH_KEY,
            cause = AuthenticationFailedCause.InvalidCredentials
        ) { challengeFunc, call ->
            challengeFunc.complete()
            call.respond(
                UnauthorizedResponse(HttpAuthHeader.bearerAuthChallenge(realm = FIREBASE_AUTH_CONFIGURATION_NAME))
            )
        }
    }

    private fun HttpAuthHeader.Companion.bearerAuthChallenge(realm: String): HttpAuthHeader =
        HttpAuthHeader.Parameterized(AUTH_SCHEME, mapOf(HttpAuthHeader.Parameters.Realm to realm))
}