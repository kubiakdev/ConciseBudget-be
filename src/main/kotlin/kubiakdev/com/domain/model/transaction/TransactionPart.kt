package kubiakdev.com.domain.model.transaction

import kotlinx.serialization.Serializable

@Serializable
data class TransactionPart(
    val title: String?,
    val userId: String,
    val cost: String,
    val transactionKey: String,
)
