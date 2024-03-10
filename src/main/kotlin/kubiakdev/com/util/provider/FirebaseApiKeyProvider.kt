package kubiakdev.com.util.provider

import kubiakdev.com.util.provider.local.properties.getLocalProperty

fun getFirebaseApiKey(): String = System.getenv("firebase_api_key") ?: getLocalProperty("firebase_api_key")