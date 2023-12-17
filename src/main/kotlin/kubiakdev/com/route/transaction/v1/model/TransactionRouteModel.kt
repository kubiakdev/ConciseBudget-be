package kubiakdev.com.route.transaction.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kubiakdev.com.domain.model.transaction.Transaction

@Serializable
data class TransactionRouteModel(
    @SerialName("id") val id: String? = null,
    @SerialName("title") val title: String,
    @SerialName("date") val date: String,
    @SerialName("category") val category: String,
    @SerialName("parts") val parts: List<TransactionPartRouteModel>,
    @SerialName("settled") val settled: String,
)

fun TransactionRouteModel.toDomainModel() = Transaction(
    id = id,
    title = title,
    date = date,
    category = category,
    parts = parts.map { it.toDomainModel() },
    settled = settled
)

fun Transaction.toRouteModel() = TransactionRouteModel(
    id = id,
    title = title,
    date = date,
    category = category,
    parts = parts.map { it.toRouteModel() },
    settled = settled,
)
