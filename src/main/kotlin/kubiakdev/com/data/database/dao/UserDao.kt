package kubiakdev.com.data.database.dao

import kubiakdev.com.data.database.database
import kubiakdev.com.data.database.model.user.User
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class UserDao {
    private val collection = database.getCollection<User>("user")

    suspend fun getById(id: String): User? = collection.findOne(User::id eq ObjectId(id).toId())

    suspend fun getByEmail(email: String): User? = collection.findOne(User::email eq email)

    suspend fun getByAuthUid(authUid: String): User? = collection.findOne(User::authUid eq authUid)

    suspend fun addUser(user: User): ObjectId? = collection.insertOne(user).insertedId?.asObjectId()?.value

    suspend fun removeById(id: String): Boolean =
        collection.deleteOne(User::id eq ObjectId(id).toId()).wasAcknowledged()
}