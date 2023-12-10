package kubiakdev.com.data.database.model.friend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kubiakdev.com.domain.model.friend.Friend
import kubiakdev.com.domain.model.friend.Friends
import kubiakdev.com.route.friends.v1.model.FriendRouteModel
import kubiakdev.com.route.friends.v1.model.FriendsRouteModel
import org.bson.types.ObjectId

@Serializable
data class FriendEntity(
    @SerialName("userId") val userId: String,
    @SerialName("username") val username: String,
)

fun Friend.toEntityModel() = FriendEntity(
    userId = userId,
    username = username,
)

fun FriendEntity.toDomainModel() = Friend(userId = userId, username = username)