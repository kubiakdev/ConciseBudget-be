package kubiakdev.com.data.database.model.transaction

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class TransactionPartEntity(
    val title: String,
    @BsonId val userId: Id<String>,
    val cost: String,
    val transactionKey: String,
)
