package kubiakdev.com.plugin

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureAuthentication() {
    install(Authentication) {
        // todo to change
        bearer("auth-bearer") {
            realm = "Access to the '/' path"
            authenticate { tokenCredential ->
                if (tokenCredential.token == "abc123") {
                    UserIdPrincipal("jetbrains")
                } else {
                    null
                }
            }
        }
    }
}