package kubiakdev.com.app.authorization.firebase

import io.ktor.server.application.*
import io.ktor.server.auth.*
import kubiakdev.com.app.authorization.firebase.util.FirebaseUser
import kubiakdev.com.app.authorization.firebase.util.firebase
import org.koin.ktor.ext.inject

fun Application.configureAuthentication() {
    val firebaseAppInitializer by inject<FirebaseAppInitializer>()
    firebaseAppInitializer.init()

    install(Authentication) {
        firebase {
            validate {
                FirebaseUser(userId = it.uid, email = it.email)
            }
        }
    }
}