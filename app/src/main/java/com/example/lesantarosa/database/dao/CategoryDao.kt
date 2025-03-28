package com.example.lesantarosa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lesantarosa.models.entities.Category
import com.example.lesantarosa.models.enums.ItemType

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(category: Category)

    @Query("DELETE FROM category WHERE categoryId = :categoryId")
    suspend fun remove(categoryId: Long)

    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    suspend fun getCategoryById(categoryId: Long): Category?

    @Query("SELECT * FROM category WHERE itemType = :itemType AND title LIKE '%' || :search || '%'")
    fun searchCategories(search: String, itemType: ItemType): LiveData<List<Category>>

}