package kubiakdev.com.data.database

import com.mongodb.kotlin.client.coroutine.MongoClient

private val client = MongoClient.create(mongoConnection)
val database = client.getDatabase("test")