package kubiakdev.com.route.user.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRouteModel(
    @SerialName("id") val id: String? = null,
    @SerialName("authId") val authUid: String,
    @SerialName("email") val email: String,
    @SerialName("publicKey") val publicKey: String,
)
