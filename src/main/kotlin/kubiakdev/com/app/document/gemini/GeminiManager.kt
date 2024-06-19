package kubiakdev.com.app.document.gemini

import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.content
import kubiakdev.com.util.provider.local.properties.getLocalProperty

class GeminiManager {

    private val generativeModel = GenerativeModel(
        modelName = MODEL_NAME,
        apiKey = System.getenv(GEMINI_API_KEY) ?: getLocalProperty(GEMINI_API_KEY)
    )

    suspend fun sendImage(imageBytes: ByteArray, message: String?): String? =
        generativeModel.generateContent(content {
            image(imageBytes)
            message?.let { text(message) }
        }).text


    private companion object {
        private const val MODEL_NAME = "gemini-1.5-flash"
        private const val GEMINI_API_KEY = "gemini_api_key"
    }
}