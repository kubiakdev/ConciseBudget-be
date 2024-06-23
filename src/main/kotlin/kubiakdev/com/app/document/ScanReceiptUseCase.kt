package kubiakdev.com.app.document

import kubiakdev.com.app.document.gemini.GeminiManager
import kubiakdev.com.domain.model.document.Receipt

class ScanReceiptUseCase(
    private val gemini: GeminiManager,
    private val optimizeImageUseCase: OptimizeImageUseCase,
    private val createReceiptUseCase: CreateReceiptUseCase,
) {

    suspend fun scanReceipt(fileBytes: ByteArray): Receipt? {
        try {
            val optimizedImageBytes = optimizeImageUseCase.optimizeImage(imageBytes = fileBytes)
            val responseText: String =
                gemini.sendImage(imageBytes = optimizedImageBytes, message = SCAN_RECEIPT_PROMPT) ?: return null
            return createReceiptUseCase.createReceipt(responseText)
        } catch (e: Exception) {
            return null
        }
    }

    private companion object {
        private const val SCAN_RECEIPT_PROMPT = """
            Na podstawie zdjęcia podaj w json następujące dane:{date, hour, title_with_shop_name, total_price, 
            items: [{full_name, full_category_name, amount, price_for_one, discount_for_item}]}. 
            Użyj tylko pełnych słów i nazw. Dobierz odpowiednie kategorie
        """
    }
}