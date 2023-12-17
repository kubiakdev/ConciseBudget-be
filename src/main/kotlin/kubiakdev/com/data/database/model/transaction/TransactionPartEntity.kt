package kubiakdev.com.data.database.model.transaction

import kotlinx.serialization.Serializable
import kubiakdev.com.domain.model.transaction.TransactionPart

@Serializable
data class TransactionPartEntity(
    val title: String,
    val userId: String,
    val cost: String,
    val transactionKey: String,
)

fun TransactionPartEntity.toDomainModel() = TransactionPart(
    title = title,
    userId = userId,
    cost = cost,
    transactionKey = transactionKey,
)

fun TransactionPart.toEntityModel() = TransactionPartEntity(
    title = title,
    userId = userId,
    cost = cost,
    transactionKey = transactionKey,
)
