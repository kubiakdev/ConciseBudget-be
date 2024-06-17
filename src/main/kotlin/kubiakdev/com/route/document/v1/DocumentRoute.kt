package kubiakdev.com.route.document.v1

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.FIREBASE_AUTH_CONFIGURATION_NAME
import kubiakdev.com.app.authentication.firebase.util.FirebaseUser
import kubiakdev.com.route.util.RouteConst
import java.io.File

fun Route.documentRoutes() {
    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        route(RouteConst.ROUTE_V1_RECEIPT_SCAN) {
            post {
                call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                var fileDescription = ""
                var fileName = ""
                val multipartData: MultiPartData

                try {
                    multipartData = call.receiveMultipart()

                    multipartData.forEachPart { part ->
                        when (part) {
                            is PartData.FormItem -> {
                                fileDescription = part.value
                            }

                            is PartData.FileItem -> {
                                fileName = part.originalFileName as String
                                val fileBytes = part.streamProvider().readBytes()
                                File("uploads/$fileName").writeBytes(fileBytes)
                            }

                            else -> {}
                        }
                        part.dispose()
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong body")
                    return@post
                }

                // todo make a response and respond
                call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
                /*                val response: Response<SignUpResponse> =
                                    signUpUseCase.signUpUser(
                                        email = body.email,
                                        password = body.password,
                                        publicKey = body.publicKey,
                                    )

                                call.respond(response.status, response.result.getOrNull() ?: response.status.description)*/
            }
        }
    }
}