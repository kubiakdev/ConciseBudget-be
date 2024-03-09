package kubiakdev.com.util.provider

import kubiakdev.com.app.authentication.firebase.firebaseApiKey

fun getFirebaseApiKey(): String = System.getenv("firebase_api_key") ?: firebaseApiKey