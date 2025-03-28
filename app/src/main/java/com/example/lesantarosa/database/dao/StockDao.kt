package com.example.lesantarosa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.models.entities.Stock

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(stock: Stock)

    @Query("DELETE FROM stock WHERE itemId = :itemId")
    suspend fun remove(itemId: Long)

    @Query("SELECT * FROM stock WHERE itemId = :itemId")
    suspend fun getStockByItemId(itemId: Long): Stock?

    @Query("SELECT * FROM stock WHERE itemType = :itemType AND itemId IN (SELECT itemId FROM item WHERE title LIKE '%' || :search || '%')")
    fun searchStocks(search: String, itemType: ItemType): LiveData<List<Stock>>

}