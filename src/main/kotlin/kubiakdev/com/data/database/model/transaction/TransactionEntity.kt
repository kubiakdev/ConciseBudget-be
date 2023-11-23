package kubiakdev.com.data.database.model.transaction

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class TransactionEntity(
    @BsonId var id: Id<String>? = null,
    val title: String,
    val date: String,
    val category: String,
    val parts: List<TransactionPartEntity>,
    val settled: Boolean,
)
