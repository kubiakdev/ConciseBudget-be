package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authorization.firebase.FIREBASE_AUTH
import kubiakdev.com.app.authorization.firebase.FirebaseUser
import kubiakdev.com.app.authorization.sign.up.SignUpBody
import kubiakdev.com.app.authorization.sign.up.SignUpUserUseCase

fun Route.authorizationRoutes() {
    route("/user/sign-up") {
        post {
            val body = call.receive<SignUpBody>()
            SignUpUserUseCase.signUpUser(email = body.email, password = body.password)
        }
    }

    authenticate(FIREBASE_AUTH) {
        get("/authenticated") {
            val user: FirebaseUser =
                call.principal() ?: return@get call.respond(HttpStatusCode.Unauthorized)
            call.respond("User is authenticated: $user")
        }
    }
}