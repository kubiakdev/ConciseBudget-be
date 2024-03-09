package kubiakdev.com.data.database

import com.mongodb.kotlin.client.coroutine.MongoClient

private val client = MongoClient.create(getMongoConnection())
val database = client.getDatabase("test")

private fun getMongoConnection(): String = System.getenv("mongo_connection") ?: mongoConnection