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
import kubiakdev.com.app.document.ScanReceiptUseCase
import kubiakdev.com.route.util.RouteConst
import org.koin.ktor.ext.inject
import java.io.File

fun Route.documentRoutes() {
    val scanReceiptUseCase by inject<ScanReceiptUseCase>()

    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        route(RouteConst.ROUTE_V1_RECEIPT_SCAN) {
            post {
                call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                try {
                    val tempFile = File("temp")
                    call.receiveMultipart()
                        .readAllParts()
                        .filterIsInstance<PartData.FileItem>()
                        .forEach { part ->
                            tempFile.writeBytes(part.streamProvider().readBytes())
                            part.dispose()

                        }

                    scanReceiptUseCase.scanReceipt(tempFile.readBytes())


                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong body")
                    return@post
                }

                // todo make a response and respond

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