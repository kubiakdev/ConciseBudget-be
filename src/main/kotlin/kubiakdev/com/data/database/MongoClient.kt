package kubiakdev.com.data.database

import kubiakdev.com.util.mongoConnection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

private val client = KMongo.createClient(mongoConnection).coroutine
val database = client.getDatabase("test")