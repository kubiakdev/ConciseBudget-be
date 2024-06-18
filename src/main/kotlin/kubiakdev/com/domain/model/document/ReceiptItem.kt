package kubiakdev.com.domain.model.document

data class ReceiptItem(
    val name: String?,
    val category: String?,
    val amount: Int?,
    val priceForOne: Double?,
    val totalDiscount: Double?,
)
