package com.example.lesantarosa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lesantarosa.models.entities.Item

interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(item: Item)

    @Query("DELETE FROM item WHERE itemId = :itemId")
    suspend fun removeItem(itemId: Long)

    @Query("SELECT * FROM item WHERE itemId = :itemId")
    suspend fun getItemRawById(itemId: Long): Item?

    suspend fun save(item: Item)

    suspend fun remove(itemId: Long)

    suspend fun getItemById(itemId: Long): Item?

    fun searchItems(search: String): LiveData<List<Item>>

}