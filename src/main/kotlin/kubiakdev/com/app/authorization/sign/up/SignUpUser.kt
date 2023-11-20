package kubiakdev.com.app.authorization.sign.up

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord

object SignUpUserUseCase {

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
}