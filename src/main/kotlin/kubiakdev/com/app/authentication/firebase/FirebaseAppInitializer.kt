package kubiakdev.com.app.authentication.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import kubiakdev.com.domain.util.environment.Environment
import kubiakdev.com.util.provider.environment.environment
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
        if (environment == Environment.Prod) getProdServiceAccount() else getTestServiceAccount()

    private fun getTestServiceAccount(): InputStream = getServiceAccount(envName = "firebase-adminsdk-test")

    private fun getProdServiceAccount(): InputStream = getServiceAccount(envName = "firebase-adminsdk-prod")

    private fun getServiceAccount(envName: String): InputStream =
        System.getenv(envName)?.byteInputStream() ?: File("$envName.json").inputStream()

    private companion object {
        private const val DATABASE_URL = "https://concisebudget-default-rtdb.europe-west1.firebasedatabase.app"
    }
}