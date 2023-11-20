package kubiakdev.com.data.database.model.friend

import org.bson.codecs.pojo.annotations.BsonId

data class Friend(
    @BsonId val userId: String,
    val username: String,
)
