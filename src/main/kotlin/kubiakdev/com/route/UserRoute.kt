package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authorization.firebase.util.AuthenticationConst.FIREBASE_AUTH_CONFIGURATION_NAME
import kubiakdev.com.app.authorization.firebase.util.FirebaseUser
import kubiakdev.com.app.authorization.sign.`in`.SignInBodyRouteModel
import kubiakdev.com.app.authorization.sign.`in`.SignInResponse
import kubiakdev.com.app.authorization.sign.up.SignUpBodyRouteModel
import kubiakdev.com.data.database.dao.UserDao
import kubiakdev.com.domain.authorization.sign.`in`.SignInUserUseCase
import kubiakdev.com.domain.authorization.sign.up.SignUpUserUseCase
import kubiakdev.com.domain.route.model.sign.`in`.SignInBody
import kubiakdev.com.domain.route.model.sign.up.SignUpBody
import kubiakdev.com.route.model.user.UserRouteModel
import kubiakdev.com.util.Response
import kubiakdev.com.util.mapper.toDomainModel
import kubiakdev.com.util.mapper.toEntityModel
import kubiakdev.com.util.mapper.toRouteModel
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val db = UserDao()
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

    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        get("/authenticated") {
            val user: FirebaseUser =
                call.principal() ?: return@get call.respond(HttpStatusCode.Unauthorized)
            call.respond("User is authenticated: $user")
        }
    }

    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        route("/user/{id}") {
            get {
                call.principal<FirebaseUser>() ?:  return@get call.respond(HttpStatusCode.Unauthorized)

                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong id param")
                    return@get
                }

                try {
                    val user = db.getById(id)?.toRouteModel()
                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route("/user/byEmail/{email}") {
            get {
                call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong email param")
                    return@get
                }

                try {
                    val user = db.getByEmail(email)?.toRouteModel()
                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route("/user/byAuthId/{authUid}") {
            get {
                call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                val authUid = call.parameters["authUid"]
                if (authUid == null) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong authUid param")
                    return@get
                }

                try {
                    val user = db.getByAuthUid(authUid)?.toRouteModel()
                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route("/user") {
            post {
                call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val user = try {
                    call.receive<UserRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong user body")
                    return@post
                }

                try {
                    val userId = db.addUser(user.toEntityModel())
                    if (userId != null) {
                        call.respond(HttpStatusCode.Created)
                    } else {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route("/user/{id}") {
            delete {
                call.principal<FirebaseUser>() ?: return@delete call.respond(HttpStatusCode.Unauthorized)

                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong id param")
                    return@delete
                }

                try {
                    val removed = db.removeById(id)
                    if (removed) {
                        call.respond(HttpStatusCode.NoContent)
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Object not removed successfully")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }
    }
}