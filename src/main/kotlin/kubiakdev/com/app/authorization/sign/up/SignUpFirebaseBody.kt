package kubiakdev.com.app.authorization.sign.up

import kotlinx.serialization.Serializable

@Serializable
data class SignUpFirebaseBody(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean,
)
