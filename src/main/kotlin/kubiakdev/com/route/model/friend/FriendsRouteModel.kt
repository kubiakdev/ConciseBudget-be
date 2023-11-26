package kubiakdev.com.route.model.friend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendsRouteModel(
    @SerialName("id") val id: String? = null,
    @SerialName("ownerId") val ownerId: String,
    @SerialName("friends") val friends: List<FriendRouteModel>,
)
