package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.app.authorization.firebase.util.FIREBASE_AUTH
import kubiakdev.com.app.authorization.firebase.util.FirebaseUser
import kubiakdev.com.data.database.dao.TransactionDao
import kubiakdev.com.route.model.transaction.TransactionRouteModel
import kubiakdev.com.util.mapper.toEntityModel
import kubiakdev.com.util.mapper.toRouteModel

fun Route.transactionRoutes() {
    val db = TransactionDao()

    authenticate(FIREBASE_AUTH) {
        route("/transactions/{userId}") {
            get {
                call.principal<FirebaseUser>() ?: return@get call.respond(HttpStatusCode.Unauthorized)

                val userId = call.parameters["userId"]
                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Wrong userId param")
                    return@get
                }

                try {
                    val transactions = db.loadAll(userId).map { it.toRouteModel() }
                    call.respond(HttpStatusCode.OK, transactions)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, e.toString())
                }
            }
        }

        route("/transaction") {
            post {
                call.principal<FirebaseUser>() ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val transaction = try {
                    call.receive<TransactionRouteModel>()
                } catch (e: Exception) {
                    val c = e
                    call.respond(HttpStatusCode.BadRequest, "Wrong transaction body")
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
        }

        route("/transaction") {
            patch {
                call.principal<FirebaseUser>() ?: return@patch call.respond(HttpStatusCode.Unauthorized)

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
        }

        route("/transaction/{id}") {
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
