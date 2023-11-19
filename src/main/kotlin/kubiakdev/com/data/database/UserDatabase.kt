package kubiakdev.com.data.database

import kubiakdev.com.data.database
import kubiakdev.com.data.model.user.User
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

private val collection = database.getCollection<User>("user")

suspend fun getById(id: String): User? {
    val bsonId: Id<String> = ObjectId(id).toId()
    return collection.findOne(User::id eq bsonId)
}

suspend fun getByEmail(email: String): User? = collection.findOne(User::email eq email)
