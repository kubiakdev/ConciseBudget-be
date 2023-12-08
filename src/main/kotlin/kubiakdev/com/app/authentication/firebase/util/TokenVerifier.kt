package kubiakdev.com.app.authentication.firebase.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.AUTH_SCHEME

class TokenVerifier {

    suspend fun verifyFirebaseIdToken(
        call: ApplicationCall,
        authHeader: HttpAuthHeader,
        principalCreationMethod: suspend ApplicationCall.(FirebaseToken) -> Principal?
    ): Principal? {
        val token: FirebaseToken? = try {
            if (authHeader.authScheme == AUTH_SCHEME && authHeader is HttpAuthHeader.Single) {
                withContext(Dispatchers.IO) {
                    FirebaseAuth.getInstance().verifyIdToken(authHeader.blob)
                }
            } else {
                null
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }

        return token?.let { principalCreationMethod(call, it) }
    }
}