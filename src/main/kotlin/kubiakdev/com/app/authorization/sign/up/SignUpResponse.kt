package kubiakdev.com.app.authorization.sign.up

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("authId") val id: String,
    @SerialName("email") val email: String,
    @SerialName("token") val token: String,
    @SerialName("refreshToken") val refreshToken: String,
)
