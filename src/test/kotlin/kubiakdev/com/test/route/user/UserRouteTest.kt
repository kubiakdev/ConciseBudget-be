package kubiakdev.com.test.route.user

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kubiakdev.com.app.authentication.sign.`in`.SignInBodyRouteModel
import kubiakdev.com.app.authentication.sign.`in`.SignInResponse
import kubiakdev.com.route.util.RouteConst.ROUTE_V1_SIGN_IN
import kubiakdev.com.util.TestConst.EXAMPLE_EMAIL
import kubiakdev.com.util.TestConst.EXAMPLE_PASSWORD
import kubiakdev.com.util.provider.json
import kubiakdev.com.util.testWithUserCreation
import org.junit.Ignore
import kotlin.test.Test

class AuthenticatedRouteTest {

    @Test
    fun `GIVEN incorrect signing in data WHEN signing in THEN 400 bad request`() = testWithUserCreation {
        client.post(ROUTE_V1_SIGN_IN) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(Unit))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }

    @Test
    fun `GIVEN correct signing in data WHEN signing in THEN 200 ok`() = testWithUserCreation {
        val exampleBody = SignInBodyRouteModel(email = it.email, password = EXAMPLE_PASSWORD)
        client.post(ROUTE_V1_SIGN_IN) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(exampleBody))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val content = json.decodeFromString<SignInResponse>(bodyAsText())
            assertEquals(EXAMPLE_EMAIL, content.email)
        }
    }

    @Ignore("It's currently blocking other tests to pass")
    @Test
    fun `GIVEN a lot of requests WHEN signing in a lot THEN 423 rate limit working`() = testWithUserCreation {
        val exampleBody = SignInBodyRouteModel(email =it.email, password = EXAMPLE_PASSWORD)
        repeat(10) {
            client.post(ROUTE_V1_SIGN_IN) {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(exampleBody))
            }
        }

        client.post(ROUTE_V1_SIGN_IN) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(exampleBody))
        }.apply {
            assertEquals(HttpStatusCode.TooManyRequests, status)
        }
    }
}