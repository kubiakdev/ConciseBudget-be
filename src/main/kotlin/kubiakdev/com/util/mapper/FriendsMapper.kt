package kubiakdev.com.util.mapper

import kubiakdev.com.data.database.model.friend.FriendEntity
import kubiakdev.com.data.database.model.friend.FriendsEntity
import kubiakdev.com.route.model.friend.FriendRouteModel
import kubiakdev.com.route.model.friend.FriendsRouteModel
import org.bson.types.ObjectId

fun FriendEntity.toRouteModel() = FriendRouteModel(userId = userId, username = username)

fun FriendsEntity.toRouteModel() = FriendsRouteModel(
    id = id!!.toString(),
    ownerId = ownerId,
    friends = friends.map { it.toRouteModel() }
)

fun FriendsRouteModel.toEntityModel() = FriendsEntity(
    id = ObjectId(id),
    ownerId = ownerId,
    friends = friends.map { it.toEntityModel() }
)

fun FriendRouteModel.toEntityModel() = FriendEntity(
    userId = userId,
    username = username,
)
