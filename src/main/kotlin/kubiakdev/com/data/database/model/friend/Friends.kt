package kubiakdev.com.data.database.model.friend

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Friends(
    @BsonId var id: Id<String>? = null,
    val ownerId: String,
    val friends: List<Friend>,
)
