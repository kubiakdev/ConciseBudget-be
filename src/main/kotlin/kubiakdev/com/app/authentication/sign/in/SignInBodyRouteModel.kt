package kubiakdev.com.app.authentication.sign.`in`

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInBodyRouteModel(
    @SerialName("email") val email: String?,
    @SerialName("password") val password: String?,
)
