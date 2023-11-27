package kubiakdev.com.domain.model.friend

data class Friends(
    var id: String,
    val ownerId: String,
    val friends: List<Friend>,
)
