package kubiakdev.com.data.database.dao

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import kotlinx.coroutines.flow.toList
import kubiakdev.com.data.database.database
import kubiakdev.com.data.database.model.transaction.TransactionEntity
import kubiakdev.com.data.database.model.transaction.TransactionPartEntity
import org.bson.types.ObjectId

class TransactionDao {
    private val collection = database.getCollection<TransactionEntity>("transaction")

    suspend fun loadAll(userId: String): List<TransactionEntity> =
        collection.find(
            eq(
                "parts.userId",
                userId
            )
        ).toList()

    suspend fun create(transaction: TransactionEntity): ObjectId? =
        collection.insertOne(transaction).insertedId?.asObjectId()?.value

    suspend fun update(transaction: TransactionEntity): Boolean =
        collection.findOneAndUpdate(
            eq("_id", transaction.id),
            Updates.combine(
                Updates.set(TransactionEntity::title.name, transaction.title),
                Updates.set(TransactionEntity::date.name, transaction.date),
                Updates.set(TransactionEntity::category.name, transaction.category),
                Updates.set(TransactionEntity::parts.name, transaction.parts),
                Updates.set(TransactionEntity::settled.name, transaction.settled),
            )
        ) != null

    suspend fun removeById(id: String): Boolean =
        collection.deleteOne(eq("_id", ObjectId(id))).wasAcknowledged()
}