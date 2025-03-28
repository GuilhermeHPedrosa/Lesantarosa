package com.example.lesantarosa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.lesantarosa.models.data.IngredientItem
import com.example.lesantarosa.models.entities.Ingredient
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.ui.fragment.formatEntity

@Dao
interface IngredientDao: ItemDao {

    // ===================== Ingredient Queries =====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveIngredient(ingredient: Ingredient)

    @Query("DELETE FROM ingredient WHERE itemId = :itemId")
    suspend fun removeIngredient(itemId: Long)

    @Query("SELECT * FROM ingredient WHERE itemId = :itemId")
    suspend fun getIngredientByItemId(itemId: Long): Ingredient?

    // ===================== Overridden ItemDao Queries =====================

    @Transaction
    override suspend fun save(item: Item) {
        if (item !is IngredientItem) throw IllegalArgumentException("Invalid Ingredient")

        saveItem(item)
        saveIngredient(item.formatEntity())
    }

    @Transaction
    override suspend fun remove(itemId: Long) {
        removeItem(itemId)
        removeIngredient(itemId)
    }

    @Transaction
    override suspend fun getItemById(itemId: Long): Item? {
        val item = getItemRawById(itemId)
        val ingredient = getIngredientByItemId(itemId)

        return if(item != null && ingredient != null) {
            IngredientItem.createIngredient(item, ingredient.brand, ingredient.supplier, ingredient.nutritionalInfo)
        } else null
    }

    @Query("SELECT item.* FROM item INNER JOIN ingredient ON item.itemId = ingredient.itemId WHERE title LIKE '%' || :search || '%'")
    override fun searchItems(search: String): LiveData<List<Item>>

}