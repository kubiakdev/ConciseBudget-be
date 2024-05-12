package kubiakdev.com.app.authentication.refresh

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    @SerialName("token") val token: String,
    @SerialName("refreshToken") val refreshToken: String,
)

fun RefreshTokenFirebaseResponse.toFinalResponseModel() = RefreshTokenResponse(
    token = token,
    refreshToken = refreshToken
)
