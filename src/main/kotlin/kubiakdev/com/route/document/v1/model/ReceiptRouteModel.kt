package kubiakdev.com.route.document.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiptRouteModel(
    @SerialName("title") val title: String?,
    @SerialName("date") val date: String?,
    @SerialName("hour") val hour: String?,
    @SerialName("price") val price: Double?,
    @SerialName("items") val items: List<ReceiptItemRouteModel>?,
)
