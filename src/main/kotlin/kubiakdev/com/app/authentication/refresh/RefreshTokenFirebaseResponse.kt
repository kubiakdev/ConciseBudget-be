package kubiakdev.com.app.authentication.refresh

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenFirebaseResponse(
    @SerialName("id_token") val token: String,
    @SerialName("refresh_token") val refreshToken: String,
)