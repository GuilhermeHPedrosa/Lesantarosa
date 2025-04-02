package com.example.lesantarosa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.entities.Order

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(order: Order)

    @Query("DELETE FROM `order` WHERE orderId == :orderId")
    suspend fun remove(orderId: Long)

    @Query("SELECT * FROM `order` WHERE orderId == :orderId")
    suspend fun geOrderById(orderId: Long): Order?

    @Query("SELECT * FROM `order` WHERE title LIKE '%' || :search || '%'")
    fun searchOrders(search: String): LiveData<List<Order>>

}