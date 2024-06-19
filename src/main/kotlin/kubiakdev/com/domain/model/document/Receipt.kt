package kubiakdev.com.domain.model.document

data class Receipt(
    val title: String?,
    val date: String?,
    val price: Double?,
    val items: List<ReceiptItem>?,
)
