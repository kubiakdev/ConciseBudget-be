package kubiakdev.com.data.model.transaction

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class TransactionPart(
    val title: String,
    @BsonId val userId: Id<String>,
    val cost: String,
    val transactionKey: String,
)
