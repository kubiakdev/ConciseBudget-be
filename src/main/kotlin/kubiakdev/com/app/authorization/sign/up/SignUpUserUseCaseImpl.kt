package kubiakdev.com.app.authorization.sign.up

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kubiakdev.com.app.authorization.firebase.firebaseApiKey
import kubiakdev.com.domain.authorization.sign.up.SignUpUserUseCase
import kubiakdev.com.util.Response

class SignUpUserUseCaseImpl : SignUpUserUseCase {

    // todo make it global
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun signUpUser(email: String, password: String): Response<SignUpResponse> {
        return try {
            createFirebaseUser(email, password)
        } catch (e: Exception) {
            e.printStackTrace()
            Response(Result.failure(e), HttpStatusCode.InternalServerError)
        }
    }

    private suspend fun createFirebaseUser(email: String, password: String): Response<SignUpResponse> {
        // todo make it global
        val client = HttpClient(engine = OkHttpEngine(OkHttpConfig())) {
            // todo install missing json component
        }


        val body = SignUpFirebaseBody(email, password, returnSecureToken = true)
        val bodyJson = Json.encodeToString(body)

        val response: HttpResponse = client.request(
            url = Url("$SIGN_UP_FIREBASE_URL?key=$firebaseApiKey"),
            block = {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(bodyJson)
            }
        )


        return try {
            if (response.status.isSuccess()) {
                Response(
                    Result.success(json.decodeFromString<SignUpResponse>(response.bodyAsText())),
                    HttpStatusCode.Created
                )
            } else {
                println("Error: ${response.status}")
                Response(Result.failure(Throwable(response.status.description)), response.status)
            }
        } catch (e: Exception) {
            Response(Result.failure(Throwable(e)), response.status)
        }
    }

    private companion object {
        private const val SIGN_UP_FIREBASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signUp"
    }
}