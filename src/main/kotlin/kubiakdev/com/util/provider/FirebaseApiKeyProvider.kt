package kubiakdev.com.util.provider

import kubiakdev.com.domain.util.environment.Environment
import kubiakdev.com.util.provider.environment.environment
import kubiakdev.com.util.provider.local.properties.getLocalProperty

fun getFirebaseApiKey(): String = System.getenv("firebase_api_key") ?: getLocalFirebaseApiKey()

private fun getLocalFirebaseApiKey(): String = if (environment == Environment.Prod) {
    getLocalProperty("firebase_api_key_prod")
} else {
    getLocalProperty("firebase_api_key_test")
}