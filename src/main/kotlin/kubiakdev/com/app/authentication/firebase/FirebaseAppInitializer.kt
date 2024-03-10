package kubiakdev.com.app.authentication.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.File
import java.io.InputStream

class FirebaseAppInitializer {

    fun init() {
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(getFirebaseOptions())
        }
    }

    private fun getFirebaseOptions(): FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(getServiceAccount()))
        .setDatabaseUrl(DATABASE_URL)
        .build()

    private fun getServiceAccount(): InputStream =
        System.getenv("firebase_adminsdk")?.byteInputStream()
            ?: File("firebase-adminsdk.json").inputStream()

    private companion object {
        private const val DATABASE_URL = "https://concisebudget-default-rtdb.europe-west1.firebasedatabase.app"
    }
}