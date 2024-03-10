package kubiakdev.com.plugin

import io.ktor.server.application.*
import io.ktor.server.plugins.ratelimit.*
import kubiakdev.com.domain.util.environment.Environment
import kubiakdev.com.util.provider.environment.environment
import kotlin.time.Duration.Companion.seconds

fun Application.configureRateLimiting() {
    install(RateLimit) {
        global {
            rateLimiter(limit = getLimitNumber(), refillPeriod = 60.seconds)
        }
    }
}

private fun getLimitNumber() = if (environment == Environment.Prod) 10 else 100