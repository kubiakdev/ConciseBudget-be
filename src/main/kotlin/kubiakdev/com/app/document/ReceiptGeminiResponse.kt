package kubiakdev.com.app.document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiptGeminiResponse(
    @SerialName("date") val date: String?,
    @SerialName("title_with_shop_name") val title: String?,
    @SerialName("total_price") val price: String?,
    @SerialName("items") val items: List<ReceiptItemGeminiResponse>?,
)
