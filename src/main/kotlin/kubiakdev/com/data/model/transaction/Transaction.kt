package kubiakdev.com.data.model.transaction

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id

data class Transaction(
    @BsonId var id: Id<String>? = null,
    val title: String,
    val date: String,
    val category: String,
    val parts: List<TransactionPart>,
    val settled: Boolean,
)
