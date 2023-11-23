package kubiakdev.com.data.database.model.user

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class UserEntity(
    @BsonId var id: Id<String>? = null,
    val authUid: String,
    val email: String,
    val publicKey: String,
)
