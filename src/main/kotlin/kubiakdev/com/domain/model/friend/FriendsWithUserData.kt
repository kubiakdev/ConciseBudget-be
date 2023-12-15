package kubiakdev.com.domain.model.friend

data class FriendsWithUserData(
    var id: String,
    val ownerId: String,
    val friends: List<FriendWithUserData>,
)

fun FriendsWithUserData.toFriendsModel() = Friends(
    id = id,
    ownerId = ownerId,
    friends = friends.map { it.toFriendModel() },
)
