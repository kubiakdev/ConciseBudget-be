package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authorization.firebase.FIREBASE_AUTH
import kubiakdev.com.app.authorization.firebase.FirebaseUser
import kubiakdev.com.app.authorization.sign.`in`.SignInBodyRouteModel
import kubiakdev.com.app.authorization.sign.`in`.SignInResponse
import kubiakdev.com.app.authorization.sign.up.SignUpBodyRouteModel
import kubiakdev.com.domain.authorization.sign.`in`.SignInUserUseCase
import kubiakdev.com.domain.authorization.sign.up.SignUpUserUseCase
import kubiakdev.com.domain.route.model.sign.`in`.SignInBody
import kubiakdev.com.domain.route.model.sign.up.SignUpBody
import kubiakdev.com.util.Response
import kubiakdev.com.util.mapper.toDomainModel
import org.koin.ktor.ext.inject

fun Route.authenticationRoutes() {

    val signUpUseCase by inject<SignUpUserUseCase>()
    val signInUseCase by inject<SignInUserUseCase>()

    route("/user/sign-up") {
        post {
            val body: SignUpBody
            try {
                body = call.receive<SignUpBodyRouteModel>().toDomainModel()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Wrong body")
                return@post
            }

            val response = signUpUseCase.signUpUser(email = body.email, password = body.password)
            call.respond(response.status, response.result.getOrNull() ?: response.result.exceptionOrNull()!!)
        }
    }

    route("/user/sign-in") {
        post {
            val body: SignInBody
            try {
                body = call.receive<SignInBodyRouteModel>().toDomainModel()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Wrong body")
                return@post
            }

            val response: Response<SignInResponse> = signInUseCase.signInUser(email = body.email, password = body.password)
            call.respond(response.status, response.result.getOrNull() ?: response.result.exceptionOrNull()!!)
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