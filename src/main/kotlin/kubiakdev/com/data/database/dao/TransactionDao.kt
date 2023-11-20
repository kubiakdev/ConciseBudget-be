package kubiakdev.com.data.database.dao

import kubiakdev.com.data.database.database
import kubiakdev.com.data.database.model.transaction.Transaction
import kubiakdev.com.data.database.model.transaction.TransactionPart
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class TransactionDao {
    private val collection = database.getCollection<Transaction>("transaction")

    suspend fun loadAll(userId: String): List<Transaction> =
        collection.find(TransactionPart::userId eq ObjectId(userId).toId()).toList()

    suspend fun create(transaction: Transaction): ObjectId? =
        collection.insertOne(transaction).insertedId?.asObjectId()?.value

    suspend fun update(transaction: Transaction): Boolean =
        collection.findOneAndReplace(filter = Transaction::id eq transaction.id, replacement = transaction) != null

    suspend fun removeById(id: String): Boolean =
        collection.deleteOne(Transaction::id eq ObjectId(id).toId()).wasAcknowledged()
}