package kubiakdev.com.domain.model.transaction

data class Transaction(
    var id: String? = null,
    val title: String,
    val date: String,
    val category: String,
    val parts: List<TransactionPart>,
    val settled: String,
)
