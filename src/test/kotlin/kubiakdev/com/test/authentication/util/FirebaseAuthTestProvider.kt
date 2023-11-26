package kubiakdev.com.test.authentication.util

import io.ktor.server.auth.*
import kubiakdev.com.app.authorization.firebase.util.FirebaseJWTAuthKey
import kubiakdev.com.app.authorization.firebase.util.FirebaseUser

class FirebaseAuthTestProvider(config: FirebaseTestConfig) : AuthenticationProvider(config) {

    private val authFunction: () -> FirebaseUser? = config.mockAuthProvider

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        val mockUser: FirebaseUser? = authFunction()
        if (mockUser != null) {
            context.principal(mockUser)
        } else {
            context.error(
                FirebaseJWTAuthKey,
                AuthenticationFailedCause.Error("User was mocked to be unauthenticated")
            )
        }
    }
}