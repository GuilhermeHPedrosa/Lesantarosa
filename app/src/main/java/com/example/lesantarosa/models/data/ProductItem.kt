package com.example.lesantarosa.models.data

import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.enums.ItemType

class ProductItem(
    itemId: Long,
    title: String,
    description: String?,
    image: String?,
    price: Double,
    expirationDate: Long?,
    createdAt: Long,
    updatedAt: Long?,
    itemType: ItemType = ItemType.PRODUCT,
    val weight: Int?,
    val packaging: String?,
    val salesCount: Int,
): Item(itemId, title, description, image, price, expirationDate, createdAt, updatedAt, itemType) {

    companion object {

        fun createProduct(item: Item, weight: Int?, packaging: String?, salesCount: Int): ProductItem {
            return ProductItem(
                item.itemId,
                item.title,
                item.description,
                item.image,
                item.price,
                item.expirationDate,
                item.createdAt,
                item.updatedAt,
                item.itemType,
                weight,
                packaging,
                salesCount,
            )
        }
    }
}