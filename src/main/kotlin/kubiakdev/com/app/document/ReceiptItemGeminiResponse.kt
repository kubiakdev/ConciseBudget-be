package kubiakdev.com.app.document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiptItemGeminiResponse(
    @SerialName("full_name") val name: String?,
    @SerialName("full_category_name") val category: String?,
    @SerialName("amount") val amount: Int?,
    @SerialName("price_for_one") val priceForOne: String?,
    @SerialName("discount_for_item") val totalDiscount: String?,
)
