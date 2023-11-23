package kubiakdev.com.data.database.model.friend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class FriendsEntity(
    @BsonId @SerialName("id") var id: Id<String>? = null,
    @SerialName("ownerId") val ownerId: String,
    @SerialName("friends") val friends: List<FriendEntity>,
)
