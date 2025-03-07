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
import kubiakdev.com.data.database.model.transaction.toEntityModel
import kubiakdev.com.route.transaction.v1.model.TransactionRouteModel
import kubiakdev.com.route.transaction.v1.model.toDomainModel
import kubiakdev.com.route.util.RouteConst
import kubiakdev.com.util.mapper.toDomainModel
import kubiakdev.com.util.mapper.toEntityModel

fun Route.transactionRoutes() {
    val db = TransactionDao()

    authenticate(FIREBASE_AUTH_CONFIGURATION_NAME) {
        route(RouteConst.ROUTE_V1_TRANSACTIONS) {
            get {
                val principal = call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                try {
                    val transactions = db.loadAll(userId = principal.authId).map { it.toDomainModel() }
                    call.respond(HttpStatusCode.OK, transactions)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }

            post {
                call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val transactions = try {
                    call.receive<List<TransactionRouteModel>>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong transactions body")
                    return@post
                }

                try {
                    val addedTransactions = transactions.map { transaction ->
                        val id = db.create(transaction.toDomainModel().toEntityModel())!!.toHexString()
                        transaction.copy(id = id)
                    }
                    call.respond(HttpStatusCode.OK,addedTransactions)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route(RouteConst.ROUTE_V1_TRANSACTION) {
            post {
                val principal = call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val transaction = try {
                    call.receive<TransactionRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong transaction body")
                    return@post
                }

                if (transaction.parts.none { it.userId == principal.authId }) {
                    call.respond(
                        HttpStatusCode.MethodNotAllowed,
                        "Cannot modify transaction which you are not a part of"
                    )
                    return@post
                }

                try {
                    val transactionId = db.create(transaction.toEntityModel())?.toHexString()
                    if (transactionId != null) {
                        call.respond(HttpStatusCode.Created, transaction.copy(id = transactionId))
                    } else {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }

            patch {
                val principal = call.principal<FirebaseUser>() ?: return@patch call.respond(HttpStatusCode.Unauthorized)

                val transaction = try {
                    call.receive<TransactionRouteModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong transaction body")
                    return@patch
                }

                if (transaction.parts.none { it.userId == principal.authId }) {
                    call.respond(
                        HttpStatusCode.MethodNotAllowed,
                        "Cannot modify transaction which you are not a part of"
                    )
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
                val principal =
                    call.principal<FirebaseUser>() ?: return@delete call.respond(HttpStatusCode.Unauthorized)

                val transactionId = call.parameters["id"]
                if (transactionId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong id param")
                    return@delete
                }

                try {
                    val removed = db.removeById(transactionId = transactionId, userId = principal.authId)
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
