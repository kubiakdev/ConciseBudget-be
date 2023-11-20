package kubiakdev.com.plugin.temp

import io.ktor.server.auth.*

// todo refactor it
fun AuthenticationConfig.firebase(
    name: String? = FIREBASE_AUTH,
    configure: FirebaseConfig.() -> Unit
) {
    val provider = FirebaseAuthProvider(FirebaseConfig(name).apply(configure))
    register(provider)
}