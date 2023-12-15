package kubiakdev.com.route.user.v1

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.AUTH_HEADER
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.AUTH_SCHEME
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.FIREBASE_AUTH_CONFIGURATION_NAME
import kubiakdev.com.app.authentication.firebase.util.FirebaseUser
import kubiakdev.com.app.authentication.sign.`in`.SignInBodyRouteModel
import kubiakdev.com.app.authentication.sign.`in`.SignInResponse
import kubiakdev.com.app.authentication.sign.up.SignUpBodyRouteModel
import kubiakdev.com.app.authentication.sign.up.SignUpResponse
import kubiakdev.com.app.user.RemoveUserUseCase
import kubiakdev.com.data.database.dao.UserDao
import kubiakdev.com.domain.authorization.sign.`in`.SignInUserUseCase
import kubiakdev.com.domain.authorization.sign.up.SignUpUserUseCase
import kubiakdev.com.domain.route.model.sign.`in`.SignInBody
import kubiakdev.com.domain.route.model.sign.up.SignUpBody
import kubiakdev.com.route.user.v1.model.UserRouteModel
import kubiakdev.com.route.util.RouteConst
import kubiakdev.com.util.Response
import kubiakdev.com.util.mapper.toDomainModel
import kubiakdev.com.util.mapper.toEntityModel
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val dao by inject<UserDao>()
    val signUpUseCase by inject<SignUpUserUseCase>()
    val signInUseCase by inject<SignInUserUseCase>()
    val removeUserUseCase by inject<RemoveUserUseCase>()

    route(RouteConst.ROUTE_V1_SIGN_UP) {
        post {
            val body: SignUpBody
            try {
                body = call.receive<SignUpBodyRouteModel>().toDomainModel()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Wrong body")
                return@post
            }

            val response: Response<SignUpResponse> =
                signUpUseCase.signUpUser(
                    email = body.email,
                    password = body.password,
                    publicKey = body.publicKey,
                )

            call.respond(response.status, response.result.getOrNull() ?: response.result.exceptionOrNull()!!)
        }
    }

    route(RouteConst.ROUTE_V1_SIGN_IN) {
        post {
            val body: SignInBody
            try {
                body = call.receive<SignInBodyRouteModel>().toDomainModel()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Wrong body")
                return@post
            }

            val response: Response<SignInResponse> =
                signInUseCase.signInUser(email = body.email, password = body.password)
            call.respond(response.status, response.result.getOrNull() ?: response.result.exceptionOrNull()!!)
        }
    }

    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        route(RouteConst.ROUTE_V1_USER) {
            get {
                val principal = call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                try {
                    val user = dao.getByAuthUid(authUid = principal.userId)?.toDomainModel()

                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }

            post {
                val principal = call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val user = try {
                    call.receive<UserRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong user body")
                    return@post
                }

                if (principal.userId != user.authUid) {
                    call.respond(HttpStatusCode.MethodNotAllowed, "Token not matches with user authId")
                    return@post
                }

                try {
                    val userId = dao.addUser(user.toDomainModel().toEntityModel())
                    if (userId != null) {
                        call.respond(HttpStatusCode.Created)
                    } else {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }

            delete {
                val principal =
                    call.principal<FirebaseUser>() ?: return@delete call.respond(HttpStatusCode.Unauthorized)

                try {
                    removeUserUseCase.removeUser(
                        authId = principal.userId,
                        token = call.request.header(AUTH_HEADER)!!.removePrefix("$AUTH_SCHEME ")
                    )
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }
    }
}