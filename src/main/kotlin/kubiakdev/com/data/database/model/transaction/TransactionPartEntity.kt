package kubiakdev.com.data.database.model.transaction

import kotlinx.serialization.Serializable

@Serializable
data class TransactionPartEntity(
    val title: String,
    val userId: String,
    val cost: String,
    val transactionKey: String,
)
