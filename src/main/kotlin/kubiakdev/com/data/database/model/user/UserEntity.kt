package kubiakdev.com.data.database.model.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class UserEntity(
    @SerialName("_id") @Contextual var id: ObjectId? = null,
    val authUid: String,
    val email: String,
    val publicKey: String,
)
