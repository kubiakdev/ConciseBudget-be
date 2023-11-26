package kubiakdev.com.data.database.model.transaction

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@Serializable
data class TransactionEntity(
    @SerialName("_id") @BsonId @Contextual @BsonProperty("_id") var id: ObjectId? = null,
    val title: String,
    val date: String,
    val category: String,
    val parts: List<TransactionPartEntity>,
    val settled: String,
)
