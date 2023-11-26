package kubiakdev.com.app.authorization.firebase.util

import io.ktor.server.auth.*

fun AuthenticationConfig.firebase(
    name: String? = FIREBASE_AUTH,
    configure: FirebaseConfig.() -> Unit
) {
    val provider = FirebaseAuthProvider(FirebaseConfig(name).apply(configure))
    register(provider)
}