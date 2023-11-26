package kubiakdev.com.route.model.transaction

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionRouteModel(
    @SerialName("id") var id: String? = null,
    @SerialName("title") val title: String,
    @SerialName("date") val date: String,
    @SerialName("category") val category: String,
    @SerialName("parts") val parts: List<TransactionPartRouteModel>,
    @SerialName("settled") val settled: String,
)
