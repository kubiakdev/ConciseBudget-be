package kubiakdev.com.test.route.user

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kubiakdev.com.app.authentication.sign.`in`.SignInBodyRouteModel
import kubiakdev.com.app.authentication.sign.`in`.SignInResponse
import kubiakdev.com.app.authentication.sign.up.SignUpBodyRouteModel
import kubiakdev.com.util.provider.json
import org.junit.Ignore
import kotlin.test.Test

class AuthenticatedRouteTest {

    @Test
    fun `GIVEN incorrect signing up data WHEN signing up THEN 400 bad request`() = testApplication {
        client.post("/v1/user/sign-up") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(Unit))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }

    @Test
    fun `GIVEN incorrect signing in data WHEN signing in THEN 400 bad request`() = testApplication {
        client.post("/v1/user/sign-in") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(Unit))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }

    @Ignore("Ignored by now because the user has to be removed at the end")
    @Test
    fun `GIVEN correct signing up data WHEN signing up THEN 201 created`() = testApplication {
        val exampleBody = SignUpBodyRouteModel(email = "testtest@wp.pl", password = "testtest")
        client.post("/v1/user/sign-up") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(exampleBody))
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
        }
    }

    @Test
    fun `GIVEN correct signing in data WHEN signing in THEN 200 ok`() = testApplication {
        val exampleBody = SignInBodyRouteModel(email = "testtest@wp.pl", password = "testtest")
        client.post("/v1/user/sign-in") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(exampleBody))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val content = json.decodeFromString<SignInResponse>(bodyAsText())
            assertEquals("testtest@wp.pl", content.email)
        }
    }

    @Test
    fun `GIVEN a lot of requests WHEN signing in a lot THEN 423 rate limit working`() = testApplication {
        val exampleBody = SignInBodyRouteModel(email = "testtest@wp.pl", password = "testtest")
        repeat(10) {
            client.post("/v1/user/sign-in") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(exampleBody))
            }
        }

        client.post("/v1/user/sign-in") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(exampleBody))
        }
            .apply {
                assertEquals(HttpStatusCode.TooManyRequests, status)
            }
    }
}