package kubiakdev.com.util.mapper

import kubiakdev.com.data.database.model.transaction.TransactionEntity
import kubiakdev.com.data.database.model.transaction.TransactionPartEntity
import kubiakdev.com.route.model.transaction.TransactionPartRouteModel
import kubiakdev.com.route.model.transaction.TransactionRouteModel
import org.bson.types.ObjectId

fun TransactionEntity.toRouteModel() = TransactionRouteModel(
    id = id?.toString(),
    title,
    date,
    category,
    parts.map { it.toRouteModel() },
    settled
)

private fun TransactionPartEntity.toRouteModel() = TransactionPartRouteModel(
    title = title,
    userId = userId,
    cost = cost,
    transactionKey = transactionKey
)

fun TransactionRouteModel.toEntityModel() = TransactionEntity(
    id = ObjectId(id),
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
