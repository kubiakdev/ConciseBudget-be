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
import kubiakdev.com.util.mapper.toRouteModel
import org.koin.ktor.ext.inject
import java.io.File

private const val TEMP_FILE_PATH = "/upload/temp"

fun Route.documentRoutes() {
    val scanReceiptUseCase by inject<ScanReceiptUseCase>()

    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        route(RouteConst.ROUTE_V1_RECEIPT_SCAN) {
            post {
                call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val tempFile = File(TEMP_FILE_PATH)
                try {
                    call.receiveMultipart()
                        .readAllParts()
                        .filterIsInstance<PartData.FileItem>()
                        .forEach { part ->
                            tempFile.writeBytes(part.streamProvider().readBytes())
                            part.dispose()
                        }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong body")
                    return@post
                }

                try {
                    val receipt = scanReceiptUseCase.scanReceipt(tempFile.readBytes())?.toRouteModel()
                    if (receipt == null) {
                        call.respond(HttpStatusCode.NotAcceptable, "Error during receipt analysis")
                    } else {
                        call.respond(HttpStatusCode.OK, receipt)
                    }
                    tempFile.delete()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }
    }
}
