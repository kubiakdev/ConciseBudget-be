package kubiakdev.com.app.authentication.refresh

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kubiakdev.com.domain.authorization.refresh.RefreshTokenUseCase
import kubiakdev.com.util.Response
import kubiakdev.com.util.provider.getFirebaseApiKey
import kubiakdev.com.util.provider.httpClient
import kubiakdev.com.util.provider.json

class RefreshTokenUseCaseImpl : RefreshTokenUseCase {

    override suspend fun refreshToken(refreshToken: String): Response<RefreshTokenResponse> {
        return try {
            refreshFirebaseToken(refreshToken)
        } catch (e: Exception) {
            e.printStackTrace()
            Response(Result.failure(e), HttpStatusCode.InternalServerError)
        }
    }

    private suspend fun refreshFirebaseToken(refreshToken: String): Response<RefreshTokenResponse> {
        val body = RefreshTokenFirebaseBody(refreshToken = refreshToken, grantType = REFRESH_TOKEN_GRANT_TYPE)
        val bodyJson = json.encodeToString(body)

        val response: HttpResponse = httpClient.request(
            url = Url("$REFRESH_TOKEN_FIREBASE_URL?key=${getFirebaseApiKey()}"),
            block = {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(bodyJson)
            }
        )


        return try {
            if (response.status.isSuccess()) {
                val firebaseResponse = json.decodeFromString<RefreshTokenFirebaseResponse>(response.bodyAsText())
                Response(
                    Result.success(firebaseResponse.toFinalResponseModel()),
                    HttpStatusCode.OK
                )
            } else {
                println("Error: ${response.status}")
                Response(Result.failure(Throwable(response.status.description)), HttpStatusCode.NotFound)
            }
        } catch (e: Exception) {
            Response(Result.failure(Throwable(e)), response.status)
        }
    }

    private companion object {
        private const val REFRESH_TOKEN_GRANT_TYPE = "refresh_token"
        private const val REFRESH_TOKEN_FIREBASE_URL = "https://securetoken.googleapis.com/v1/token"
    }
}
