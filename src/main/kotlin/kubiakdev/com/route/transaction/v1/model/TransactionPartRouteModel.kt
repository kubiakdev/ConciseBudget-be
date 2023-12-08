package kubiakdev.com.route.transaction.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionPartRouteModel(
    @SerialName("title") val title: String,
    @SerialName("userId") val userId: String,
    @SerialName("cost") val cost: String,
    @SerialName("transactionKey") val transactionKey: String,
)
