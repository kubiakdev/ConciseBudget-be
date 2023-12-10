package kubiakdev.com.util.mapper

import kubiakdev.com.data.database.model.friend.FriendEntity
import kubiakdev.com.data.database.model.friend.FriendsEntity
import kubiakdev.com.domain.model.friend.Friend
import kubiakdev.com.domain.model.friend.Friends
import kubiakdev.com.route.friends.v1.model.FriendRouteModel
import kubiakdev.com.route.friends.v1.model.FriendsRouteModel
import org.bson.types.ObjectId

// todo to remove mappers from entities to routes
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

fun FriendEntity.toDomainModel() = Friend(userId = userId, username = username)

fun FriendsEntity.toDomainModel() = Friends(
    id = id!!.toString(),
    ownerId = ownerId,
    friends = friends.map { it.toDomainModel() }
)

fun Friend.toRouteModel() = FriendRouteModel(userId = userId, username = username)

fun Friends.toRouteModel() = FriendsRouteModel(
    id = id,
    ownerId = ownerId,
    friends = friends.map { it.toRouteModel() }
)