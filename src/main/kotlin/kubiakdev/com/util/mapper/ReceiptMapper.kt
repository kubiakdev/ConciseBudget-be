package kubiakdev.com.util.mapper

import kubiakdev.com.app.document.ReceiptItemGeminiResponse
import kubiakdev.com.app.document.ReceiptGeminiResponse
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

fun ReceiptGeminiResponse.toDomainModel() = Receipt(
    title = title,
    date = date,
    price = parsePrice(price),
    items = items?.map { it.toDomainModel() }
)

fun ReceiptItemGeminiResponse.toDomainModel() = ReceiptItem(
    name = name,
    category = category,
    amount = amount,
    priceForOne = parsePrice(priceForOne),
    totalDiscount = parsePrice(totalDiscount),
)

private fun parsePrice(text: String?): Double? = text?.replace(',', '.')?.toDouble()
