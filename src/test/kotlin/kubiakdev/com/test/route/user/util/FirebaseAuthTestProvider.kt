package kubiakdev.com.test.route.user.util

import io.ktor.server.auth.*
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst
import kubiakdev.com.app.authentication.firebase.util.FirebaseUser

class FirebaseAuthTestProvider(config: FirebaseTestConfig) : AuthenticationProvider(config) {

    private val authFunction: () -> FirebaseUser? = config.mockAuthProvider

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        val mockUser: FirebaseUser? = authFunction()
        if (mockUser != null) {
            context.principal(mockUser)
        } else {
            context.error(
                AuthenticationConst.FIREBASE_JWT_AUTH_KEY,
                AuthenticationFailedCause.Error("User was mocked to be unauthenticated")
            )
        }
    }
}