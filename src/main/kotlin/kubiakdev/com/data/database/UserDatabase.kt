package kubiakdev.com.data.database

import kubiakdev.com.data.database
import kubiakdev.com.data.model.user.User

private val collection = database.getCollection<User>()

suspend fun getUserById(id: String): User? = collection.findOneById(id)