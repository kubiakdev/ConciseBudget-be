package kubiakdev.com.data.database.model.user

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class User(
    @BsonId var id: Id<String>? = null,
    val authUid: String,
    val email: String,
    val publicKey: String,
)
