package kubiakdev.com.app.authorization.sign.`in`

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kubiakdev.com.app.authorization.firebase.firebaseApiKey
import kubiakdev.com.domain.authorization.sign.`in`.SignInUserUseCase
import kubiakdev.com.util.Response
import kubiakdev.com.util.provider.httpClient
import kubiakdev.com.util.provider.json

class SignInUserUseCaseImpl : SignInUserUseCase {
    override suspend fun signInUser(email: String, password: String): Response<SignInResponse> {
        return try {
            loginFirebaseUser(email, password)
        } catch (e: Exception) {
            e.printStackTrace()
            Response(Result.failure(e), HttpStatusCode.InternalServerError)
        }
    }

    private suspend fun loginFirebaseUser(email: String, password: String): Response<SignInResponse> {
        val body = SignInFirebaseBody(email, password, returnSecureToken = true)
        val bodyJson = Json.encodeToString(body)

        val response: HttpResponse = httpClient.request(
            url = Url("$SIGN_IN_FIREBASE_URL?key=$firebaseApiKey"),
            block = {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(bodyJson)
            }
        )


        return try {
            if (response.status.isSuccess()) {
                Response(
                    Result.success(json.decodeFromString<SignInResponse>(response.bodyAsText())),
                    HttpStatusCode.OK
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
        private const val SIGN_IN_FIREBASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword"
    }
}
