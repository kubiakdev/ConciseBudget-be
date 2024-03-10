package kubiakdev.com.data.database.dao

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import kotlinx.coroutines.flow.firstOrNull
import kubiakdev.com.data.database.database
import kubiakdev.com.data.database.model.friend.FriendsEntity
import org.bson.types.ObjectId

class FriendsDao {
    private val collection = database.getCollection<FriendsEntity>("friends")

    suspend fun loadAll(userAuthId: String): FriendsEntity? =
        collection.find(eq(FriendsEntity::ownerId.name, userAuthId)).firstOrNull()

    suspend fun create(friends: FriendsEntity): ObjectId? =
        collection.insertOne(friends).insertedId?.asObjectId()?.value

    suspend fun update(friends: FriendsEntity): Boolean =
        collection.findOneAndUpdate(
            eq("_id", friends.id),
            Updates.set(FriendsEntity::friends.name, friends.friends)
        ) != null

    suspend fun remove(userAuthId: String): Boolean =
        collection.deleteOne(
            eq(FriendsEntity::ownerId.name, userAuthId)
        ).wasAcknowledged()
}