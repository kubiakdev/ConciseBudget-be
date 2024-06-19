package kubiakdev.com.route.document.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiptItemRouteModel(
    @SerialName("name") val name: String?,
    @SerialName("category") val category: String?,
    @SerialName("amount") val amount: Int?,
    @SerialName("priceForOne") val priceForOne: Double?,
    @SerialName("totalDiscount") val totalDiscount: Double?,
)
