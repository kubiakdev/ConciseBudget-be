package kubiakdev.com.route.transaction.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kubiakdev.com.domain.model.transaction.TransactionPart

@Serializable
data class TransactionPartRouteModel(
    @SerialName("title") val title: String?,
    @SerialName("userId") val userId: String,
    @SerialName("cost") val cost: String,
    @SerialName("transactionKey") val transactionKey: String,
)

fun TransactionPartRouteModel.toDomainModel() = TransactionPart(
    title = title,
    userId = userId,
    cost = cost,
    transactionKey = transactionKey,
)

fun TransactionPart.toRouteModel() = TransactionPartRouteModel(
    title = title,
    userId = userId,
    cost = cost,
    transactionKey = transactionKey
)
