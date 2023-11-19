package kubiakdev.com.data.model.user

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class User(
    @BsonId val id: Id<String>,
    val authUid: String,
    val email: String,
    val publicKey: String,
)
