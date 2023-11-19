package kubiakdev.com.data

import kubiakdev.com.data.model.friend.Friends
import kubiakdev.com.data.model.transaction.Transaction
import kubiakdev.com.mongoConnection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

private val client = KMongo.createClient(mongoConnection).coroutine
val database = client.getDatabase("test")

val transactionsCollection = database.getCollection<Transaction>()
val friendsCollection = database.getCollection<Friends>()
