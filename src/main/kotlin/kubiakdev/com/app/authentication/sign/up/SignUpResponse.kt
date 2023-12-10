package kubiakdev.com.app.authentication.sign.up

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("id") val id: String,
    @SerialName("authId") val authId: String,
    @SerialName("email") val email: String,
    @SerialName("token") val token: String,
    @SerialName("refreshToken") val refreshToken: String,
)

fun SignUpFirebaseResponse.toFinalResponseModel(dbId: String) = SignUpResponse(
    id = dbId,
    authId = id,
    email = email,
    token = token,
    refreshToken = refreshToken
)