package kubiakdev.com.app.authorization.firebase

import io.ktor.server.application.*
import io.ktor.server.auth.*

// todo refactor it
fun Application.configureFirebaseAuth() {
    install(Authentication) {
        firebase {
            validate {
                FirebaseUser(it.uid, it.email)
            }
        }
    }
}