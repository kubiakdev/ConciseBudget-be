package kubiakdev.com.domain.model.friend

data class FriendsWithUserData(
    var id: String,
    val ownerId: String,
    val friends: List<FriendWithUserData>,
)
