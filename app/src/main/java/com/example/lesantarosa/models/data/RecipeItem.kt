package com.example.lesantarosa.models.data

import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.enums.Difficulty

class RecipeItem(
    itemId: Long,
    title: String,
    description: String?,
    image: String?,
    price: Double,
    expirationDate: Long?,
    createdAt: Long,
    updatedAt: Long?,
    val yield: Int?,
    val servings: Int?,
    val preparationTime: Int?,
    val difficulty: Difficulty?
): Item(itemId, title, description, image, price, expirationDate, createdAt, updatedAt) {

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
                yield,
                servings,
                preparationTime,
                difficulty
            )
        }
    }
}