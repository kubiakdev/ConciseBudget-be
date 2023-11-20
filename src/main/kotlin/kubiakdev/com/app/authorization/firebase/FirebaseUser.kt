package kubiakdev.com.app.authorization.firebase

import io.ktor.server.auth.*

// todo refactor it
data class FirebaseUser(val userId: String = "", val email: String = "") : Principal