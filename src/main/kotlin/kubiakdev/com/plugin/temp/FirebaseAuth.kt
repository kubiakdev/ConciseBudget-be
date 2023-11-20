package kubiakdev.com.plugin.temp

import io.ktor.server.application.*
import io.ktor.server.auth.*
import kubiakdev.com.data.model.principal.FirebaseUser

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