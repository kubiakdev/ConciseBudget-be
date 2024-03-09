package kubiakdev.com.util

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.AUTH_HEADER
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.AUTH_SCHEME
import kubiakdev.com.app.authentication.sign.up.SignUpBodyRouteModel
import kubiakdev.com.app.authentication.sign.up.SignUpResponse
import kubiakdev.com.route.util.RouteConst
import kubiakdev.com.util.provider.json

fun testWithUserCreation(block: suspend ApplicationTestBuilder.(SignUpResponse) -> Unit) {
    testApplication {
        val signUpResponse = signUpTestUser()
        try {
            block(json.decodeFromString<SignUpResponse>(signUpResponse.bodyAsText()))
        } catch (e: Throwable) {
            deleteTestUser(signUpResponse)
            throw e
        }
    }
}

suspend fun ApplicationTestBuilder.signUpTestUser(): HttpResponse =
    client.post(RouteConst.ROUTE_V1_SIGN_UP) {
        contentType(ContentType.Application.Json)
        setBody(
            json.encodeToString(
                SignUpBodyRouteModel(
                    email = TestConst.EXAMPLE_EMAIL,
                    password = TestConst.EXAMPLE_PASSWORD,
                    publicKey = TestConst.EXAMPLE_PUBLIC_KEY,
                )
            )
        )
    }

suspend fun ApplicationTestBuilder.deleteTestUser(signUpResponse: HttpResponse): HttpResponse {
    val parsedResponse = json.decodeFromString<SignUpResponse>(signUpResponse.bodyAsText())
    return client.delete(RouteConst.ROUTE_V1_USER) {
        contentType(ContentType.Application.Json)
        header(AUTH_HEADER, "$AUTH_SCHEME ${parsedResponse.token}")
    }
}