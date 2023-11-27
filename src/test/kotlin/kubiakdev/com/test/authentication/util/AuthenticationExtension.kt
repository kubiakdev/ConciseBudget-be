package kubiakdev.com.test.authentication.util

import io.ktor.server.auth.*
import io.ktor.server.testing.*
import kubiakdev.com.app.authorization.firebase.util.AuthenticationConst
import kubiakdev.com.app.authorization.firebase.util.FirebaseUser

val defaultTestUser = FirebaseUser(userId = "some-user-id", email = "test@gmail.com")
fun ApplicationTestBuilder.mockAuthentication(mockAuth: () -> FirebaseUser? = { defaultTestUser }) {
    install(Authentication) {
        val provider = FirebaseAuthTestProvider(
            FirebaseTestConfig(AuthenticationConst.FIREBASE_AUTH_CONFIGURATION_NAME).apply {
                mockAuthProvider = mockAuth
            }
        )
        register(provider)
    }
}