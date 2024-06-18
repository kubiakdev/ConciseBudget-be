package kubiakdev.com.app.document

import kubiakdev.com.domain.model.document.Receipt

class ScanReceiptUseCase {

    suspend fun scanReceipt(fileBytes: ByteArray): Receipt? {
        /* todo optimize image
            send image to AI to get JSON
            extract JSON
         */
        return null
    }
}