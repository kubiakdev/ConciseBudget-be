package kubiakdev.com.route.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendsRouteModel(
    @SerialName("id") var id: String,
    @SerialName("ownerId") val ownerId: String,
    @SerialName("friends") val friends: List<FriendRouteModel>,
)
