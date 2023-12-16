package kubiakdev.com.test.route.user.util

import io.ktor.server.auth.*
import io.ktor.server.testing.*
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst
import kubiakdev.com.app.authentication.firebase.util.FirebaseUser

val defaultTestUser = FirebaseUser(authId = "some-user-id", email = "test@gmail.com")

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