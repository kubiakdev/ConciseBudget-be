package kubiakdev.com.util.mapper

import kubiakdev.com.data.database.model.transaction.TransactionEntity
import kubiakdev.com.data.database.model.transaction.TransactionPartEntity
import kubiakdev.com.route.transaction.v1.model.TransactionPartRouteModel
import kubiakdev.com.route.transaction.v1.model.TransactionRouteModel
import org.bson.types.ObjectId

fun TransactionEntity.toDomainModel() = TransactionRouteModel(
    id = id?.toString(),
    title,
    date,
    category,
    parts.map { it.toDomainModel() },
    settled
)

private fun TransactionPartEntity.toDomainModel() = TransactionPartRouteModel(
    title = title,
    userId = userId,
    cost = cost,
    transactionKey = transactionKey
)

fun TransactionRouteModel.toEntityModel() = TransactionEntity(
    id = id?.let { ObjectId(it) },
    title = title,
    date = date,
    category = category,
    parts = parts.map { it.toEntityModel() },
    settled = settled
)

private fun TransactionPartRouteModel.toEntityModel() = TransactionPartEntity(
    title = title,
    userId = userId,
    cost = cost,
    transactionKey = transactionKey
)
