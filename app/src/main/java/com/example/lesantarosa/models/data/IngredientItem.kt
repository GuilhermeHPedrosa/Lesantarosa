package com.example.lesantarosa.models.data

import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.enums.ItemType

class IngredientItem(
    itemId: Long,
    title: String,
    description: String?,
    image: String?,
    price: Double,
    expirationDate: Long?,
    createdAt: Long,
    updatedAt: Long?,
    itemType: ItemType = ItemType.INGREDIENT,
    val brand: String?,
    val supplier: String?,
    val nutritionalInfo: String?
): Item(itemId, title, description, image, price, expirationDate, createdAt, updatedAt, itemType) {

    companion object {

        fun createIngredient(item: Item, brand: String?, supplier: String?, nutritionalInfo: String?): IngredientItem {
            return IngredientItem(
                item.itemId,
                item.title,
                item.description,
                item.image,
                item.price,
                item.expirationDate,
                item.createdAt,
                item.updatedAt,
                item.itemType,
                brand,
                supplier,
                nutritionalInfo
            )
        }
    }
}