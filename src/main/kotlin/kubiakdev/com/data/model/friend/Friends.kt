package kubiakdev.com.data.model.friend

import org.bson.codecs.pojo.annotations.BsonId

data class Friends(
    @BsonId val id: String,
    @BsonId val ownerId: String,
    val friends: List<Friend>,
)
