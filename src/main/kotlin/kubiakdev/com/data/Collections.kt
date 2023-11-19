package kubiakdev.com.data

import kubiakdev.com.mongoConnection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

private val client = KMongo.createClient(mongoConnection).coroutine
val database = client.getDatabase("test")