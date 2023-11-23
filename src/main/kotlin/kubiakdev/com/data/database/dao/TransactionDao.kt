package kubiakdev.com.data.database.dao

import kubiakdev.com.data.database.database
import kubiakdev.com.data.database.model.transaction.TransactionEntity
import kubiakdev.com.data.database.model.transaction.TransactionPartEntity
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.id.toId

class TransactionDao {
    private val collection = database.getCollection<TransactionEntity>("transaction")

    suspend fun loadAll(userId: String): List<TransactionEntity> =
        collection.find(TransactionPartEntity::userId eq ObjectId(userId).toId()).toList()

    suspend fun create(transaction: TransactionEntity): ObjectId? =
        collection.insertOne(transaction).insertedId?.asObjectId()?.value

    suspend fun update(transaction: TransactionEntity): Boolean =
        collection.findOneAndReplace(filter = TransactionEntity::id eq transaction.id, replacement = transaction) != null

    suspend fun removeById(id: String): Boolean =
        collection.deleteOne(TransactionEntity::id eq ObjectId(id).toId()).wasAcknowledged()
}