package kubiakdev.com.domain.model.friends

data class Friends(
    var id: String,
    val ownerId: String,
    val friends: List<Friend>,
)
