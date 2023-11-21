package kubiakdev.com.app.authorization.sign.up

import com.google.firebase.auth.FirebaseAuth
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kubiakdev.com.app.authorization.firebase.firebaseApiKey

object SignUpUserUseCase {

    private const val SIGN_UP_FIREBASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signUp"
    suspend fun signUpUser(email: String, password: String): SignUpResponse? {
        return try {
            createFirebaseUser(email, password)
            null

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private val json = Json { ignoreUnknownKeys = true }

    private suspend fun createFirebaseUser(email: String, password: String) {
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
                val response = json.decodeFromString<SignUpResponse>(response.bodyAsText())
            } else {
                println("Error: ${response.status}")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}