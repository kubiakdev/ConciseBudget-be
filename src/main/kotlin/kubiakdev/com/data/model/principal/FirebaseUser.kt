package kubiakdev.com.data.model.principal

import io.ktor.server.auth.*

// todo refactor it
data class FirebaseUser(val userId: String = "", val email: String = "") : Principal