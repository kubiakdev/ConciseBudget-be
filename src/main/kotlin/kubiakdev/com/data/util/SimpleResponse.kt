package kubiakdev.com.data.util

data class SimpleResponse<T>(
    val status: Boolean,
    val message: String,
    val data: T
)
