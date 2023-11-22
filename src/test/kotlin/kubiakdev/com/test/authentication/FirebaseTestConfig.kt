package kubiakdev.com.test.authentication

import io.ktor.server.auth.*
import kubiakdev.com.app.authorization.firebase.FirebaseUser

class FirebaseTestConfig(name: String?) : AuthenticationProvider.Config(name) {

    var mockAuthProvider: () -> FirebaseUser? = { null }

}
