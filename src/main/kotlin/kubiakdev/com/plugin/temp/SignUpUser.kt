package kubiakdev.com.plugin.temp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord

// todo refactor it
fun signUpUser(email: String, password: String): UserRecord? {
    val auth = FirebaseAuth.getInstance()
    return try {
        auth.createUser(
            UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}