package kubiakdev.com.app.authentication.sign.`in`

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInFirebaseBody(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("returnSecureToken") val returnSecureToken: Boolean,
)
