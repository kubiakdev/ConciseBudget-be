package kubiakdev.com.data.database.model.transaction

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kubiakdev.com.domain.model.transaction.Transaction
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@Serializable
data class TransactionEntity(
    @BsonId @Contextual @BsonProperty("_id") var id: ObjectId? = null,
    val title: String,
    val date: String,
    val category: String,
    val parts: List<TransactionPartEntity>,
    val settled: String,
)

fun TransactionEntity.toDomainModel() = Transaction(
    id = id?.toHexString(),
    title = title,
    date = date,
    category = category,
    parts = parts.map { it.toDomainModel() },
    settled = settled,
)

fun Transaction.toEntityModel() = TransactionEntity(
    id = id?.let { ObjectId(id) },
    title = title,
    date = date,
    category = category,
    parts = parts.map { it.toEntityModel() },
    settled = settled,
)
