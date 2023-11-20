package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authorization.firebase.FIREBASE_AUTH
import kubiakdev.com.data.model.principal.FirebaseUser
import kubiakdev.com.data.model.principal.SignInData
import kubiakdev.com.plugin.temp.signUpUser

fun Route.authorizationRoutes(){
    route("/user/sign-up") {
        post {
            val user = call.receive<SignInData>()
            signUpUser(user.email, user.password)
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