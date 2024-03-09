package kubiakdev.com.app.user

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kubiakdev.com.data.database.dao.FriendsDao
import kubiakdev.com.data.database.dao.UserDao
import kubiakdev.com.util.Response
import kubiakdev.com.util.provider.getFirebaseApiKey
import kubiakdev.com.util.provider.httpClient

class RemoveUserUseCase(
    private val userDao: UserDao,
    private val friendsDao: FriendsDao,
) {

    suspend fun removeUser(authId: String, token: String): Response<Unit> {
        return try {
            // todo break operation in case of failure
            removeFirebaseUser(authId = authId, token = token)
            userDao.removeByAuthId(authId = authId)
            friendsDao.remove(userAuthId = authId)
            Response(Result.success(Unit), HttpStatusCode.NoContent)
        } catch (e: Exception) {
            e.printStackTrace()
            Response(Result.failure(e), HttpStatusCode.InternalServerError)
        }
    }

    private suspend fun removeFirebaseUser(authId: String, token: String): Response<Unit> {
        val body = RemoveUserFirebaseBody(authId = authId, token = token)
        val bodyJson = Json.encodeToString(body)

        val response: HttpResponse = httpClient.request(
            url = Url("$DELETE_USER_FIREBASE_URL?key=${getFirebaseApiKey()}"),
            block = {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(bodyJson)
            }
        )


        return try {
            if (response.status.isSuccess()) {
                Response(
                    Result.success(Unit),
                    HttpStatusCode.NoContent
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
        private const val DELETE_USER_FIREBASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:delete"
    }
}