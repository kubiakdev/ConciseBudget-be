package kubiakdev.com.util.mapper

import kubiakdev.com.domain.model.document.Receipt
import kubiakdev.com.domain.model.document.ReceiptItem
import kubiakdev.com.route.document.v1.model.ReceiptItemRouteModel
import kubiakdev.com.route.document.v1.model.ReceiptRouteModel

fun Receipt.toRouteModel() = ReceiptRouteModel(
    title = title,
    date = date,
    price = price,
    items = items?.map { it.toRouteModel() },
)

fun ReceiptItem.toRouteModel() = ReceiptItemRouteModel(
    name = name,
    category = category,
    amount = amount,
    priceForOne = priceForOne,
    totalDiscount = totalDiscount,
)