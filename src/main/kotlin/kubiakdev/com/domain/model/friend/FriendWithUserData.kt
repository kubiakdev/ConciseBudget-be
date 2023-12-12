package kubiakdev.com.domain.model.friend

data class FriendWithUserData(
    val userId: String,
    val username: String,
    val email: String,
    val publicKey: String,
)
