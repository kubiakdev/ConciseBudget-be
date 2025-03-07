package kubiakdev.com.app.authentication.sign.up

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kubiakdev.com.domain.authorization.sign.up.SignUpUserUseCase
import kubiakdev.com.util.Response
import kubiakdev.com.util.provider.getFirebaseApiKey
import kubiakdev.com.util.provider.httpClient
import kubiakdev.com.util.provider.json

class SignUpUserUseCaseImpl(
    private val createUserUseCase: CreateUserUseCase,
) : SignUpUserUseCase {

    override suspend fun signUpUser(email: String, password: String, publicKey: String): Response<SignUpResponse> {
        return try {
            val response = createFirebaseUser(email, password)

            if (response.status == HttpStatusCode.BadRequest) {
                Response(Result.failure(Exception("User with given email already exists")), HttpStatusCode.BadRequest)
            } else {
                val result = response.result.getOrNull()!!
                val id = createUserUseCase.createUserInDatabase(
                    authId = result.id,
                    email = email,
                    publicKey = publicKey,
                )

                Response(Result.success(result.toFinalResponseModel(id!!)), HttpStatusCode.Created)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response(Result.failure(e), HttpStatusCode.InternalServerError)
        }
    }

    private suspend fun createFirebaseUser(email: String, password: String): Response<SignUpFirebaseResponse> {
        val body = SignUpFirebaseBody(email, password, returnSecureToken = true)
        val bodyJson = json.encodeToString(body)

        val response: HttpResponse = httpClient.request(
            url = Url("$SIGN_UP_FIREBASE_URL?key=${getFirebaseApiKey()}"),
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
                    Result.success(firebaseResponse),
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
