package kubiakdev.com.data.database.dao

import kubiakdev.com.data.database.database
import kubiakdev.com.data.database.model.user.UserEntity
import org.bson.types.ObjectId

// todo fix methods after converting to mongo driver
class UserDao {
    private val collection = database.getCollection<UserEntity>("user")

    suspend fun getById(id: String): UserEntity? = null
//        collection.findOne(UserEntity::id eq ObjectId(id).toId())

    suspend fun getByEmail(email: String): UserEntity? =
        null
//    collection.findOne(UserEntity::email eq email)

    suspend fun getByAuthUid(authUid: String): UserEntity? = null
//        collection.findOne(UserEntity::authUid eq authUid)

    suspend fun addUser(user: UserEntity): ObjectId? = collection.insertOne(user).insertedId?.asObjectId()?.value

    suspend fun removeById(id: String): Boolean = true
//        collection.deleteOne(UserEntity::id eq ObjectId(id).toId()).wasAcknowledged()
}