package kubiakdev.com.data.database.model.friend

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@Serializable
data class FriendsEntity(
    @SerialName("_id") @BsonId @Contextual @BsonProperty("_id") var id: ObjectId? = null,
    @SerialName("ownerId") val ownerId: String,
    @SerialName("friends") val friends: List<FriendEntity>,
)
