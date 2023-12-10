package kubiakdev.com.route.friends.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kubiakdev.com.domain.model.friend.Friend
import kubiakdev.com.domain.model.friend.Friends

@Serializable
data class FriendRouteModel(
    @SerialName("userId") val userId: String,
    @SerialName("username") val username: String,
)

fun FriendRouteModel.toDomainModel() = Friend(
    userId = userId,
    username = username,
)

fun Friend.toRouteModel() = FriendRouteModel(userId = userId, username = username)
