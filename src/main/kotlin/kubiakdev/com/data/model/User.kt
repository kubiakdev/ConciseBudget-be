package kubiakdev.com.data.model

import org.bson.codecs.pojo.annotations.BsonId

data class User(
    @BsonId
    val id: String,
    val authUid: String,
    val email: String,
    val publicKey: String,
)
