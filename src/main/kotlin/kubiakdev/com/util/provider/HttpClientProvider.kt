package kubiakdev.com.util.provider

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.time.Duration

val httpClient = HttpClient(engine = OkHttpEngine(getOkHttpConfig())) {
    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}

private fun getOkHttpConfig() = OkHttpConfig().apply {
    config {
        connectTimeout(Duration.ofSeconds(10))
        build()
    }
}