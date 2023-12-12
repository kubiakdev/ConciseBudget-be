package kubiakdev.com.route.friends.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kubiakdev.com.domain.model.friend.FriendsWithUserData

@Serializable
data class FriendsWithUserDataRouteModel(
    @SerialName("id") val id: String? = null,
    @SerialName("ownerId") val ownerId: String,
    @SerialName("friends") val friends: List<FriendWithUserDataRouteModel>,
)

fun FriendsWithUserData.toRouteModel() = FriendsWithUserDataRouteModel(
    id = id,
    ownerId = ownerId,
    friends = friends.map { it.toRouteModel() },
)
