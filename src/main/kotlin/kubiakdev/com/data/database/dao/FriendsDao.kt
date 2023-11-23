package kubiakdev.com.data.database.dao

import kubiakdev.com.data.database.database
import kubiakdev.com.data.database.model.friend.FriendsEntity
import org.bson.types.ObjectId
import org.litote.kmongo.eq

class FriendsDao {
    private val collection = database.getCollection<FriendsEntity>("friends")

    suspend fun loadAll(ownerUserId: String): FriendsEntity? =
        collection.findOne(FriendsEntity::ownerId eq ownerUserId)

    suspend fun create(friends: FriendsEntity): ObjectId? = collection.insertOne(friends).insertedId?.asObjectId()?.value

    suspend fun update(friends: FriendsEntity): Boolean =
        collection.findOneAndReplace(filter = FriendsEntity::id eq friends.id, replacement = friends) != null
}