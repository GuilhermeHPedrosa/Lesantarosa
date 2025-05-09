package com.example.lesantarosa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lesantarosa.models.data.CartSummary
import com.example.lesantarosa.models.data.StockItem
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.models.entities.Stock
import com.example.lesantarosa.models.entities.StockMovement

@Dao
interface StockDao {

    // ===================== Stock =====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(stock: Stock)

    @Query("UPDATE stock set currentStock = :newQuantity WHERE itemId = :itemId")
    suspend fun update(itemId: Long, newQuantity: Int)

    @Query("DELETE FROM stock WHERE itemId = :itemId")
    suspend fun remove(itemId: Long)

    @Query("SELECT * FROM stock WHERE itemId = :itemId")
    suspend fun getStockByItemId(itemId: Long): Stock?

    @Query("SELECT i.itemId, i.title, i.image, s.currentStock, s.reorderLevel, s.measureUnit FROM item i LEFT JOIN stock s ON i.itemId = s.itemId WHERE i.itemType = :itemType AND i.title LIKE '%' || :search || '%'")
    fun searchStocks(search: String, itemType: ItemType): LiveData<List<StockItem>>

    // ===================== Stock Movement =====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovement(stockMovement: StockMovement)

    @Query("DELETE FROM stock_movement WHERE stockMovementId = :stockMovementId")
    suspend fun removeMovement(stockMovementId: Long)

    @Query("SELECT * FROM stock_movement WHERE itemId = :itemId")
    fun getStockMovementsByItemId(itemId: Long): LiveData<List<StockMovement>>

}