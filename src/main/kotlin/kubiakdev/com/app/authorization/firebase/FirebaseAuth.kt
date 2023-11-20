package kubiakdev.com.app.authorization.firebase

import io.ktor.server.application.*
import io.ktor.server.auth.*
import kubiakdev.com.data.model.principal.FirebaseUser
import kubiakdev.com.plugin.temp.firebase

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