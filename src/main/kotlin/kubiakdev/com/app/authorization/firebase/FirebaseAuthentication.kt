package kubiakdev.com.app.authorization.firebase

import io.ktor.server.application.*
import io.ktor.server.auth.*
import kubiakdev.com.app.authorization.firebase.util.AuthenticationConst.FIREBASE_AUTH_CONFIGURATION_NAME
import kubiakdev.com.app.authorization.firebase.util.FirebaseAuthProvider
import kubiakdev.com.app.authorization.firebase.util.FirebaseConfig
import kubiakdev.com.app.authorization.firebase.util.FirebaseUser
import org.koin.ktor.ext.inject

fun Application.configureAuthentication() {
    val firebaseAppInitializer by inject<FirebaseAppInitializer>()
    firebaseAppInitializer.init()

    install(Authentication) {
        val provider = FirebaseAuthProvider(FirebaseConfig(FIREBASE_AUTH_CONFIGURATION_NAME))
        provider.setPrincipalCreationMethod { FirebaseUser(userId = it.uid, email = it.email) }
        register(provider)
    }
}