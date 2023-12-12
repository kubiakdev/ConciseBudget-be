package kubiakdev.com.route.friends.v1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kubiakdev.com.domain.model.friend.FriendWithUserData

@Serializable
data class FriendWithUserDataRouteModel(
    @SerialName("userId") val userId: String,
    @SerialName("username") val username: String,
    @SerialName("email") val email: String,
    @SerialName("publicKey") val publicKey: String,
)

fun FriendWithUserData.toRouteModel() = FriendWithUserDataRouteModel(
    userId = userId,
    username = username,
    email = email,
    publicKey = publicKey,
)
