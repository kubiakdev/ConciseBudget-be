package kubiakdev.com.data.model.transaction

import org.bson.codecs.pojo.annotations.BsonId

data class TransactionPart(
    val title: String,
    @BsonId val userId: String,
    val cost: String,
    val transactionKey: String,
)
