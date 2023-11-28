package kubiakdev.com.app.authorization.sign.up

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpFirebaseResponse(
    @SerialName("localId") val id: String,
    @SerialName("email") val email: String,
    @SerialName("idToken") val token: String,
    @SerialName("refreshToken") val refreshToken: String,
)
