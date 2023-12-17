package kubiakdev.com.test.route.transaction

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst
import kubiakdev.com.data.database.model.transaction.toEntityModel
import kubiakdev.com.domain.model.transaction.Transaction
import kubiakdev.com.domain.model.transaction.TransactionPart
import kubiakdev.com.route.transaction.v1.model.TransactionRouteModel
import kubiakdev.com.route.transaction.v1.model.toRouteModel
import kubiakdev.com.route.util.RouteConst
import kubiakdev.com.util.provider.json
import kubiakdev.com.util.testWithUserCreation
import org.junit.Test

class TransactionRouteTest {

    @Test
    fun `GIVEN logged in user THEN verify whole transaction flow THEN success`() =
        testWithUserCreation { signUpResponse ->
            // load all transactions
            client.get(RouteConst.ROUTE_V1_TRANSACTIONS) {
                contentType(ContentType.Application.Json)
                header(AuthenticationConst.AUTH_HEADER, "${AuthenticationConst.AUTH_SCHEME} ${signUpResponse.token}")
            }.apply {
                assertEquals(HttpStatusCode.OK, status)
                val response = json.decodeFromString<List<TransactionRouteModel>>(bodyAsText())
                assertEquals(response, emptyList<TransactionRouteModel>())
            }

            val exampleTransaction = Transaction(
                id = null,
                title = "example title",
                date = "example date",
                category = "example category",
                parts = listOf(
                    TransactionPart(
                        title = "example title",
                        userId = signUpResponse.authId,
                        cost = "example cost",
                        transactionKey = "example transactionKey"
                    )
                ),
                settled = "example settled"
            )

            // add transaction
            val response = client.post(RouteConst.ROUTE_V1_TRANSACTION) {
                contentType(ContentType.Application.Json)
                header(AuthenticationConst.AUTH_HEADER, "${AuthenticationConst.AUTH_SCHEME} ${signUpResponse.token}")
                setBody(json.encodeToString(exampleTransaction.toEntityModel()))
            }.apply {
                assertEquals(HttpStatusCode.Created, status)
            }

            val transactionId = response.bodyAsText()

            val exampleTransactionPartsPatch = listOf(
                TransactionPart(
                    title = "example title",
                    userId = signUpResponse.authId,
                    cost = "example cost",
                    transactionKey = "example transactionKey"
                ),
                TransactionPart(
                    title = "example title 2",
                    userId = signUpResponse.authId,
                    cost = "example cost 2",
                    transactionKey = "example transactionKey 2"
                )
            )

            // patch transaction
            client.patch(RouteConst.ROUTE_V1_TRANSACTION) {
                contentType(ContentType.Application.Json)
                header(AuthenticationConst.AUTH_HEADER, "${AuthenticationConst.AUTH_SCHEME} ${signUpResponse.token}")
                setBody(
                    json.encodeToString(
                        exampleTransaction.copy(
                            id = transactionId,
                            parts = exampleTransactionPartsPatch,
                        ).toRouteModel()
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.NoContent, status)
            }

            // load transaction second time to patched transaction
            client.get(RouteConst.ROUTE_V1_TRANSACTIONS) {
                contentType(ContentType.Application.Json)
                header(AuthenticationConst.AUTH_HEADER, "${AuthenticationConst.AUTH_SCHEME} ${signUpResponse.token}")
            }.apply {
                assertEquals(HttpStatusCode.OK, status)
                assertEquals(
                    json.decodeFromString<List<TransactionRouteModel>>(bodyAsText()),
                    listOf(
                        exampleTransaction.copy(
                            id = transactionId,
                            parts = exampleTransactionPartsPatch
                        ).toRouteModel()
                    )
                )
            }

            // remove transaction
            client.delete(RouteConst.ROUTE_V1_TRANSACTION) {
                parameter("id", transactionId)
                contentType(ContentType.Application.Json)
                header(AuthenticationConst.AUTH_HEADER, "${AuthenticationConst.AUTH_SCHEME} ${signUpResponse.token}")
            }.apply {
                assertEquals(HttpStatusCode.NoContent, status)
            }

            // verify transaction not exists
            client.get(RouteConst.ROUTE_V1_TRANSACTIONS) {
                contentType(ContentType.Application.Json)
                header(AuthenticationConst.AUTH_HEADER, "${AuthenticationConst.AUTH_SCHEME} ${signUpResponse.token}")
            }.apply {
                assertEquals(HttpStatusCode.OK, status)
                assertEquals(
                    json.decodeFromString<List<TransactionRouteModel>>(bodyAsText()),
                    emptyList<TransactionRouteModel>()
                )
            }
        }
}
