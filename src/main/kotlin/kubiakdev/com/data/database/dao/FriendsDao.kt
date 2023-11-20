package kubiakdev.com.data.database.dao

import kubiakdev.com.data.database
import kubiakdev.com.data.database.model.friend.Friends
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class FriendsDao {
    private val collection = database.getCollection<Friends>("friends")

    suspend fun loadAll(ownerUserId: String): Friends? =
        collection.findOne(Friends::ownerId eq ObjectId(ownerUserId).toId())

    suspend fun create(friends: Friends): ObjectId? = collection.insertOne(friends).insertedId?.asObjectId()?.value

    suspend fun update(friends: Friends): Boolean =
        collection.findOneAndReplace(filter = Friends::id eq friends.id, replacement = friends) != null
}