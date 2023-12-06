package kubiakdev.com.test.authentication.util

import io.ktor.server.auth.*
import kubiakdev.com.app.authentication.firebase.util.FirebaseUser

class FirebaseTestConfig(name: String?) : AuthenticationProvider.Config(name) {

    var mockAuthProvider: () -> FirebaseUser? = { null }
}
