package kubiakdev.com.data.model.transaction

import org.bson.codecs.pojo.annotations.BsonId

data class Transaction(
    @BsonId val id: String,
    val title: String,
    val date: String,
    val category: String,
    val parts: List<TransactionPart>,
    val settled: Boolean,
)
