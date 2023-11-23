package kubiakdev.com.test.authentication

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kubiakdev.com.app.authorization.firebase.FirebaseUser
import kotlin.test.Test

class AuthenticatedRouteTest {

    @Test
    fun `GIVEN incorrect signing up data WHEN signing up THEN 400 bad request`() = testApplication {
        client.post("/user/sign-up") {
            headers {
                append("Content-Type", ContentType.Application.Json)
            }
            setBody(Json.encodeToString(Unit))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }

    @Test
    fun `GIVEN incorrect signing in data WHEN signing in THEN 400 bad request`() = testApplication {
        client.post("/user/sign-in") {
            headers {
                append("Content-Type", ContentType.Application.Json)
            }
            setBody(Json.encodeToString(Unit))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }

//    route("/user/sign-up") {
//        post {
//            val body = call.receive<SignUpBody>()
//            val response = SignUpUserUseCase.signUpUser(email = body.email, password = body.password)
//            call.respond(response.status, response.result.getOrNull() ?: response.result.exceptionOrNull()!!)
//        }
//    }


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