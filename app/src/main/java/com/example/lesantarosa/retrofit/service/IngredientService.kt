package com.example.lesantarosa.retrofit.service

import com.example.lesantarosa.models.data.IngredientItem
import com.example.lesantarosa.models.entities.Item
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IngredientService: ItemService {

    @POST("ingredient")
    suspend fun saveIngredient(@Body ingredientItem: IngredientItem)

    @PUT("ingredient/{itemId}")
    suspend fun updateIngredient(@Path("itemId") itemId: Long, @Body ingredientItem: IngredientItem)

    @DELETE("ingredient/{itemId}")
    suspend fun removeIngredient(@Path("itemId") itemId: Long)

    override suspend fun save(item: Item) {
        if (item !is IngredientItem) throw IllegalArgumentException("Invalid Ingredient")

        saveIngredient(item)
    }

    override suspend fun update(item: Item) {
        if (item !is IngredientItem) throw IllegalArgumentException("Invalid Ingredient")

        updateIngredient(item.itemId, item)
    }

    override suspend fun remove(itemId: Long) {
        removeIngredient(itemId)
    }
}