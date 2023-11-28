package kubiakdev.com.app.authorization.sign.up

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kubiakdev.com.domain.authorization.sign.up.SignUpUserUseCase
import kubiakdev.com.util.Response
import kubiakdev.com.util.provider.httpClient
import kubiakdev.com.util.provider.json

class SignUpUserUseCaseImpl : SignUpUserUseCase {

    override suspend fun signUpUser(email: String, password: String): Response<SignUpResponse> {
        return try {
            createFirebaseUser(email, password)
        } catch (e: Exception) {
            e.printStackTrace()
            Response(Result.failure(e), HttpStatusCode.InternalServerError)
        }
    }

    private suspend fun createFirebaseUser(email: String, password: String): Response<SignUpResponse> {
        val body = SignUpFirebaseBody(email, password, returnSecureToken = true)
        val bodyJson = Json.encodeToString(body)

        val response: HttpResponse = httpClient.request(
            url = Url("$SIGN_UP_FIREBASE_URL?key=${System.getenv("firebase_api_key")}"),
            block = {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(bodyJson)
            }
        )


        return try {
            if (response.status.isSuccess()) {
                val firebaseResponse = json.decodeFromString<SignUpFirebaseResponse>(response.bodyAsText())
                Response(
                    Result.success(firebaseResponse.toFinalResponseModel()),
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
