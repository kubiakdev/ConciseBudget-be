package kubiakdev.com.data.database

import com.mongodb.kotlin.client.coroutine.MongoClient
import kubiakdev.com.domain.util.environment.Environment
import kubiakdev.com.util.provider.local.properties.getLocalProperty
import kubiakdev.com.util.provider.environment.environment

private val client = MongoClient.create(getMongoConnection())
val database = client.getDatabase(if (environment == Environment.Test) "test" else "prod")

private fun getMongoConnection(): String = System.getenv("mongo_connection") ?: getLocalProperty("mongo_connection")