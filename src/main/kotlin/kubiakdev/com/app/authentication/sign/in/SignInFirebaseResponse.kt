package kubiakdev.com.app.authentication.sign.`in`

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInFirebaseResponse(
    @SerialName("localId") val id: String,
    @SerialName("email") val email: String,
    @SerialName("idToken") val token: String,
    @SerialName("refreshToken") val refreshToken: String,
)
