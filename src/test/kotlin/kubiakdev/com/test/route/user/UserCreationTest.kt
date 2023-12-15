package kubiakdev.com.test.route.user

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kubiakdev.com.route.util.RouteConst.ROUTE_V1_SIGN_UP
import kubiakdev.com.util.deleteTestUser
import kubiakdev.com.util.provider.json
import kubiakdev.com.util.signUpTestUser
import kotlin.test.Test

class UserCreationTest {

    @Test
    fun `GIVEN incorrect signing up data WHEN signing up THEN 400 bad request`() = testApplication {
        client.post(ROUTE_V1_SIGN_UP) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(Unit))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }

    @Test
    fun `GIVEN correct signing up data and removing user WHEN signing up and removing THEN success`() =
        testApplication {
            val signUpResponse = signUpTestUser().apply {
                assertEquals(HttpStatusCode.Created, status)
            }

            deleteTestUser(signUpResponse).apply {
                assertEquals(HttpStatusCode.NoContent, status)
            }
        }
}