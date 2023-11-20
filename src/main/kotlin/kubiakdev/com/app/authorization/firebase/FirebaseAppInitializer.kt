package kubiakdev.com.app.authorization.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.InputStream

object FirebaseAppInitializer {
    private const val DATABASE_URL = "https://concisebudget-default-rtdb.europe-west1.firebasedatabase.app"
    private const val JSON_FILE_NAME = "firebase-adminsdk.json"

    private val serviceAccount: InputStream? =
        this::class.java.classLoader.getResourceAsStream(JSON_FILE_NAME)

    private val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl(DATABASE_URL)
        .build()

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)
}