package kubiakdev.com.app.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveUserFirebaseBody(
    @SerialName("localId") val authId: String,
    @SerialName("idToken") val token: String,
)
