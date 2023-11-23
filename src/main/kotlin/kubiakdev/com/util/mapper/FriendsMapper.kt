package kubiakdev.com.util.mapper

import kubiakdev.com.data.database.model.friend.FriendEntity
import kubiakdev.com.data.database.model.friend.FriendsEntity
import kubiakdev.com.route.model.FriendRouteModel
import kubiakdev.com.route.model.FriendsRouteModel
import org.bson.types.ObjectId


fun FriendEntity.toRouteModel() = FriendRouteModel(userId = userId, username = username)

fun FriendsEntity.toRouteModel() = FriendsRouteModel(
    id = id!!.cast<ObjectId>().toString(),
    ownerId = ownerId,
    friends = friends.map { it.toRouteModel() }
)