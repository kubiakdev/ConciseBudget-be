package kubiakdev.com.data.database.model.friend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class FriendEntity(
    @BsonId @SerialName("userId") val userId: String,
    @SerialName("username") val username: String,
)
