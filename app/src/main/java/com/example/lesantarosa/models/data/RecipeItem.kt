package com.example.lesantarosa.models.data

import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.enums.Difficulty
import com.example.lesantarosa.models.enums.ItemType

class RecipeItem(
    itemId: Long,
    title: String,
    description: String?,
    image: String?,
    price: Double,
    expirationDate: Long?,
    createdAt: Long,
    updatedAt: Long?,
    itemType: ItemType = ItemType.RECIPE,
    val yield: Int?,
    val servings: Int?,
    val preparationTime: Int?,
    val difficulty: Difficulty?
): Item(itemId, title, description, image, price, expirationDate, createdAt, updatedAt, itemType) {

    companion object {

        fun createRecipe(item: Item, yield: Int?, servings: Int?, preparationTime: Int?, difficulty: Difficulty?): RecipeItem {
            return RecipeItem(
                item.itemId,
                item.title,
                item.description,
                item.image,
                item.price,
                item.expirationDate,
                item.createdAt,
                item.updatedAt,
                item.itemType,
                yield,
                servings,
                preparationTime,
                difficulty
            )
        }
    }
}