package kubiakdev.com.data.database.model.transaction

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class TransactionEntity(
    @SerialName("_id") @Contextual var id: ObjectId? = null,
    val title: String,
    val date: String,
    val category: String,
    val parts: List<TransactionPartEntity>,
    val settled: Boolean,
)
