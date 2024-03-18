package kubiakdev.com.app.authentication.firebase.util

import com.google.firebase.auth.AuthErrorCode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst.AUTH_SCHEME

class TokenVerifier(
    private val firebaseAuth: FirebaseAuth,
) {

    suspend fun verifyFirebaseIdToken(
        call: ApplicationCall,
        authHeader: HttpAuthHeader,
        principalCreationMethod: suspend ApplicationCall.(FirebaseToken) -> Principal?,
    ): Principal? {
        var token: FirebaseToken? = null
        try {
            token = extractAndVerifyToken(authHeader)
        } catch (e: FirebaseAuthException) {
            if (e.authErrorCode == AuthErrorCode.EXPIRED_ID_TOKEN) {
                // todo handle token expiration
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return token?.let { principalCreationMethod(call, it) }
    }

    private suspend fun extractAndVerifyToken(authHeader: HttpAuthHeader): FirebaseToken? =
        if (authHeader.authScheme == AUTH_SCHEME && authHeader is HttpAuthHeader.Single) {
            withContext(Dispatchers.IO) {
                firebaseAuth.verifyIdToken(authHeader.blob)
            }
        } else {
            null
        }
}