package kubiakdev.com.app.document

import kubiakdev.com.domain.model.document.Receipt
import kubiakdev.com.util.mapper.toDomainModel
import kubiakdev.com.util.provider.json

class CreateReceiptUseCase {

    fun createReceipt(textResponse: String): Receipt? {
        return try {
            val jsonText = getJsonText(textResponse)
            json.decodeFromString<ReceiptGeminiResponse>(jsonText).toDomainModel()
        } catch (e: Exception) {
            // todo log exception to measure gemini failures
            null
        }
    }

    private fun getJsonText(textResponse: String): String = textResponse.substring(
        startIndex = textResponse.indexOfFirst { it == '{' },
        endIndex = textResponse.indexOfLast { it == '}' } + 1
    )
}