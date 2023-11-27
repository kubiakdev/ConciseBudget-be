package kubiakdev.com.app.authorization.sign.up

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpBodyRouteModel(
    @SerialName("email") val email: String?,
    @SerialName("password") val password: String?,
)