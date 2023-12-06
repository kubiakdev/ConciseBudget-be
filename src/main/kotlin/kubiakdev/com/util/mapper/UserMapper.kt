package kubiakdev.com.util.mapper

import kubiakdev.com.data.database.model.user.UserEntity
import kubiakdev.com.route.user.v1.model.UserRouteModel
import org.bson.types.ObjectId

fun UserEntity.toRouteModel() = UserRouteModel(
    id = id?.toString(),
    authUid = authUid,
    email = email,
    publicKey = publicKey
)

fun UserRouteModel.toEntityModel() = UserEntity(
    id = id?.let { ObjectId(it) },
    authUid = authUid,
    email = email,
    publicKey = publicKey
)
