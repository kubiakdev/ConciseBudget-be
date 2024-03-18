package kubiakdev.com.app.authentication.firebase

import io.ktor.server.application.*
import io.ktor.server.auth.*
import kubiakdev.com.app.authentication.firebase.util.*
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.FIREBASE_AUTH_CONFIGURATION_NAME
import org.koin.ktor.ext.inject

fun Application.configureAuthentication() {
    val firebaseAppInitializer by inject<FirebaseAppInitializer>()
    val tokenParser by inject<TokenParser>()
    val tokenVerifier by inject<TokenVerifier>()
    firebaseAppInitializer.init()

    install(Authentication) {
        val provider = FirebaseAuthProvider(
            config = FirebaseConfig(FIREBASE_AUTH_CONFIGURATION_NAME),
            tokenVerifier = tokenVerifier,
            tokenParser = tokenParser,
        )
        provider.setPrincipalCreationMethod { FirebaseUser(authId = it.uid, email = it.email) }
        register(provider)
    }
}