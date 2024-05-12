package kubiakdev.com.app.authentication.refresh

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenFirebaseBody(
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("grant_type") val grantType: String,
)
