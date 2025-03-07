package kubiakdev.com.data.database.dao

import com.mongodb.client.model.Filters.eq
import kotlinx.coroutines.flow.firstOrNull
import kubiakdev.com.data.database.database
import kubiakdev.com.data.database.model.user.UserEntity
import org.bson.types.ObjectId

class UserDao {
    private val collection = database.getCollection<UserEntity>("user")

    suspend fun getByEmail(email: String): UserEntity? =
        collection.find(eq(UserEntity::email.name, email)).firstOrNull()

    suspend fun getByAuthUid(authUid: String): UserEntity? =
        collection.find(eq(UserEntity::authUid.name, authUid)).firstOrNull()

    suspend fun addUser(user: UserEntity): ObjectId? = collection.insertOne(user).insertedId?.asObjectId()?.value

    suspend fun removeByAuthId(authId: String): Boolean =
        collection.deleteOne(eq("authUid", authId)).wasAcknowledged()
}