package kubiakdev.com.data.database.model.friend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kubiakdev.com.domain.model.friend.Friend

@Serializable
data class FriendEntity(
    @SerialName("userId") val userId: String,
    @SerialName("username") val username: String,
)

fun Friend.toEntityModel() = FriendEntity(
    userId = authId,
    username = username,
)

fun FriendEntity.toDomainModel() = Friend(authId = userId, username = username)