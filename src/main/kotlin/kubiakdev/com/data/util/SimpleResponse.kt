package kubiakdev.com.data.util

data class SimpleResponse<T>(
    val message: String,
    val data: T
)
