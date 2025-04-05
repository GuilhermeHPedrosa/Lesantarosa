package com.example.lesantarosa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lesantarosa.models.data.OrdersSummary
import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.models.enums.OrderStatus

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(order: Order)

    @Query("DELETE FROM `order` WHERE orderId == :orderId")
    suspend fun remove(orderId: Long)

    @Query("SELECT * FROM `order` WHERE orderId == :orderId")
    suspend fun getOrderById(orderId: Long): Order?

    @Query("SELECT * FROM `order` WHERE (:status IS NULL OR orderStatus = :status) AND title LIKE '%' || :search || '%' ORDER BY createdAt DESC")
    fun searchOrders(search: String, status: OrderStatus?): LiveData<List<Order>>

    @Query("SELECT COUNT(*) as totalQuantity, SUM(finalAmount) as finalAmount FROM `order`")
    fun getOrdersSummary(): LiveData<OrdersSummary>

}