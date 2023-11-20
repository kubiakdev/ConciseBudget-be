package kubiakdev.com.plugin.temp

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.InputStream

// todo refactor it
object FirebaseAdmin {
    private val serviceAccount: InputStream? =
        this::class.java.classLoader.getResourceAsStream("firebase-adminsdk.json")

    private val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://concisebudget-default-rtdb.europe-west1.firebasedatabase.app")
        .build()

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)
}