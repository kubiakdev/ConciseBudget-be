package kubiakdev.com.data.database

import com.mongodb.kotlin.client.coroutine.MongoClient

private val client = MongoClient.create(System.getenv("mongo_connection"))
val database = client.getDatabase("test")