package kubiakdev.com.util

import io.ktor.http.*

data class Response<T>(
    val result: Result<T>,
    val status: HttpStatusCode,
)
