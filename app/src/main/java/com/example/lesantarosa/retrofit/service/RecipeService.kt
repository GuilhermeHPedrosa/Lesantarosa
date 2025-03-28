package com.example.lesantarosa.retrofit.service

import com.example.lesantarosa.models.data.RecipeItem
import com.example.lesantarosa.models.entities.Item
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecipeService: ItemService {

    @POST("recipe")
    suspend fun saveRecipe(@Body recipeItem: RecipeItem)

    @PUT("recipe/{itemId}")
    suspend fun updateRecipe(@Path("itemId") itemId: Long, @Body recipeItem: RecipeItem)

    @DELETE("recipe/{itemId}")
    suspend fun removeRecipe(@Path("itemId") itemId: Long)

    override suspend fun save(item: Item) {
        if (item !is RecipeItem) throw IllegalArgumentException("Invalid Recipe")

        saveRecipe(item)
    }

    override suspend fun update(item: Item) {
        if (item !is RecipeItem) throw IllegalArgumentException("Invalid Product")

        updateRecipe(item.itemId, item)
    }

    override suspend fun remove(itemId: Long) {
        removeRecipe(itemId)
    }
}