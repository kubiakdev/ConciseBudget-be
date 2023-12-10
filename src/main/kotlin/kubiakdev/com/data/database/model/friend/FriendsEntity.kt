package kubiakdev.com.data.database.model.friend

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kubiakdev.com.domain.model.friend.Friends
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@Serializable
data class FriendsEntity(
    @SerialName("_id") @BsonId @Contextual @BsonProperty("_id") var id: ObjectId? = null,
    @SerialName("ownerId") val ownerId: String,
    @SerialName("friends") val friends: List<FriendEntity>,
)

fun Friends.toEntityModel() = FriendsEntity(
    id = ObjectId(id),
    ownerId = ownerId,
    friends = friends.map { it.toEntityModel() }
)

fun FriendsEntity.toDomainModel() = Friends(
    id = id!!.toString(),
    ownerId = ownerId,
    friends = friends.map { it.toDomainModel() }
)