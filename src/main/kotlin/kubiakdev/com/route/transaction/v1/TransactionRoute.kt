package kubiakdev.com.route.transaction.v1

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.FIREBASE_AUTH_CONFIGURATION_NAME
import kubiakdev.com.app.authentication.firebase.util.FirebaseUser
import kubiakdev.com.data.database.dao.TransactionDao
import kubiakdev.com.route.transaction.v1.model.TransactionRouteModel
import kubiakdev.com.util.mapper.toDomainModel
import kubiakdev.com.util.mapper.toEntityModel

fun Route.transactionRoutes() {
    val db = TransactionDao()

    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        route("/v1/transactions") {
            get {
                val principal = call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                try {
                    val transactions = db.loadAll(userId = principal.userId).map { it.toDomainModel() }
                    call.respond(HttpStatusCode.OK, transactions)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route("/v1/transaction") {
            post {
                val principal = call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val transaction = try {
                    call.receive<TransactionRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong transaction body")
                    return@post
                }

                if (transaction.parts.none { it.userId == principal.userId }) {
                    call.respond(
                        HttpStatusCode.MethodNotAllowed,
                        "Cannot modify transaction which you are not a part of"
                    )
                    return@post
                }

                try {
                    val transactionId = db.create(transaction.toEntityModel())
                    if (transactionId != null) {
                        call.respond(HttpStatusCode.Created)
                    } else {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }

            patch {
                call.principal<FirebaseUser>() ?: return@patch call.respond(HttpStatusCode.Unauthorized)

                // todo add validation from principal that only transaction part user can edit transaction
                val transaction = try {
                    call.receive<TransactionRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong transaction body")
                    return@patch
                }

                try {
                    val updated = db.update(transaction.toEntityModel())
                    if (updated) {
                        call.respond(HttpStatusCode.NoContent)
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Object not updated successfully")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }

            delete {
                call.principal<FirebaseUser>() ?: return@delete call.respond(HttpStatusCode.Unauthorized)

                // todo add validation from principal that only transaction part user can remove transaction
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
