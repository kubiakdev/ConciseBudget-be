package kubiakdev.com.route.model.friend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendRouteModel(
    @SerialName("userId") val userId: String,
    @SerialName("username") val username: String,
)
