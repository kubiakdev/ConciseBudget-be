package kubiakdev.com.data.database.model.friend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendEntity(
    @SerialName("userId") val userId: String,
    @SerialName("username") val username: String,
)
