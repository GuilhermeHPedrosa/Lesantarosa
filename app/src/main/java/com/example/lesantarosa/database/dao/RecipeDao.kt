package com.example.lesantarosa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.data.RecipeItem
import com.example.lesantarosa.models.entities.Recipe
import com.example.lesantarosa.ui.fragment.formatEntity

@Dao
interface RecipeDao: ItemDao {

    // ===================== Recipe Queries =====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecipe(recipe: Recipe)

    @Query("DELETE FROM recipe WHERE itemId = :itemId")
    suspend fun removeRecipe(itemId: Long)

    @Query("SELECT * FROM recipe WHERE itemId = :itemId")
    suspend fun getRecipeByItemId(itemId: Long): Recipe?

    // ===================== Overridden ItemDao Queries =====================

    @Transaction
    override suspend fun save(item: Item) {
        if (item !is RecipeItem) throw IllegalArgumentException("Invalid Recipe")

        saveItem(item)
        saveRecipe(item.formatEntity())
    }

    @Transaction
    override suspend fun remove(itemId: Long) {
        removeItem(itemId)
        removeRecipe(itemId)
    }

    @Transaction
    override suspend fun getItemById(itemId: Long): Item? {
        val item = getItemRawById(itemId)
        val recipe = getRecipeByItemId(itemId)

        return if(item != null && recipe != null) {
            RecipeItem.createRecipe(item, recipe.yield, recipe.servings, recipe.preparationTime, recipe.difficulty)
        } else null
    }

    @Query("SELECT item.* FROM item INNER JOIN recipe ON item.itemId = recipe.itemId WHERE title LIKE '%' || :search || '%'")
    override fun searchItems(search: String): LiveData<List<Item>>

}