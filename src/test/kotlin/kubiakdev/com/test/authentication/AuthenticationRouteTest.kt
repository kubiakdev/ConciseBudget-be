package kubiakdev.com.test.authentication

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import kubiakdev.com.app.authorization.firebase.FirebaseUser
import kotlin.test.Test

class AuthenticatedRouteTest {

    // todo test there the whole flow with also removing the user
    @Test
    fun `authenticated route - is authenticated`() = testApplication {
        val user = FirebaseUser("some id", "Andrew")

//        mockAuthentication { user }
//        routing { authorizationRoutes() }

        client.get("/authenticated", {
            headers {
                append("Authorization", "Bearer $EXAMPLE_TOKEN")
            }
        }).apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("User is authenticated: $user", bodyAsText())
        }
    }

    @Test
    fun `authenticated route - is unauthorized`() = testApplication {
//        mockAuthentication { null }
//        routing { authorizationRoutes() }

        client.get("/authenticated", {
            headers {
                append("Authorization", "Bearer $EXAMPLE_TOKEN")
            }
        }).apply {
            assertEquals(HttpStatusCode.Unauthorized, status)
        }
    }

    private val EXAMPLE_TOKEN = "put there example token"
}