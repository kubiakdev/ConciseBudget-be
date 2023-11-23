package kubiakdev.com.util.provider

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*


val httpClient = HttpClient(engine = OkHttpEngine(OkHttpConfig())) {}