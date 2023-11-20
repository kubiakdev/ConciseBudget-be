package kubiakdev.com.app.authorization.firebase

import io.ktor.server.auth.*
import kubiakdev.com.app.authorization.firebase.FIREBASE_AUTH
import kubiakdev.com.app.authorization.firebase.FirebaseAuthProvider
import kubiakdev.com.app.authorization.firebase.FirebaseConfig

// todo refactor it
fun AuthenticationConfig.firebase(
    name: String? = FIREBASE_AUTH,
    configure: FirebaseConfig.() -> Unit
) {
    val provider = FirebaseAuthProvider(FirebaseConfig(name).apply(configure))
    register(provider)
}