package kubiakdev.com.app.authorization.sign.`in`

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("authId") val id: String,
    @SerialName("email") val email: String,
    @SerialName("token") val token: String,
    @SerialName("refreshToken") val refreshToken: String,
)
