package kubiakdev.com.data.database

import kubiakdev.com.data.database
import kubiakdev.com.data.model.user.User
import org.litote.kmongo.eq

private val collection = database.getCollection<User>("user")

suspend fun getByEmail(email: String): User? = collection.findOne(User::email eq email)