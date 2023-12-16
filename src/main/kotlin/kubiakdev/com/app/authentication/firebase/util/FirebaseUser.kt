package kubiakdev.com.app.authentication.firebase.util

import io.ktor.server.auth.*

data class FirebaseUser(val authId: String = "", val email: String = "") : Principal