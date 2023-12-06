package kubiakdev.com.route.friends.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendRouteModel(
    @SerialName("userId") val userId: String,
    @SerialName("username") val username: String,
)
