package kubiakdev.com.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kubiakdev.com.data.database.dao.TransactionDao
import kubiakdev.com.data.database.model.transaction.Transaction

fun Route.transactionRoutes() {
    val db = TransactionDao()

    route("/transaction/{userId}") {
        get {
            val userId = call.parameters["userId"]
            if (userId == null) {
                call.respond(HttpStatusCode.BadRequest, "Wrong userId param")
                return@get
            }

            try {
                val transactions = db.loadAll(userId)
                call.respond(HttpStatusCode.OK, transactions)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, e.toString())
            }
        }
    }

    route("/transaction") {
        post {
            val transaction = try {
                call.receive<Transaction>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Wrong transaction body")
                return@post
            }

            try {
                val transactionId = db.create(transaction)
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
            val transaction = try {
                call.receive<Transaction>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Wrong transaction body")
                return@patch
            }

            try {
                val updated = db.update(transaction)
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
