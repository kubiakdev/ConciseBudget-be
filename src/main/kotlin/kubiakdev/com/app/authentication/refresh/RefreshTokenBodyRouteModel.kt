package kubiakdev.com.app.authentication.refresh

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenBodyRouteModel(@SerialName("refreshToken") val refreshToken: String)
