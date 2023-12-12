package kubiakdev.com.util.mapper

import kubiakdev.com.data.database.model.user.UserEntity
import kubiakdev.com.domain.model.user.User
import kubiakdev.com.route.user.v1.model.UserRouteModel
import org.bson.types.ObjectId

fun UserEntity.toDomainModel() = User(
    id = id?.toString(),
    authUid = authUid,
    email = email,
    publicKey = publicKey
)

fun User.toEntityModel() = UserEntity(
    id = id?.let { ObjectId(it) },
    authUid = authUid,
    email = email,
    publicKey = publicKey
)

fun User.toRouteModel() = UserRouteModel(
    id = id?.toString(),
    authUid = authUid,
    email = email,
    publicKey = publicKey
)

fun UserRouteModel.toDomainModel() = User(
    id = id,
    authUid = authUid,
    email = email,
    publicKey = publicKey
)
