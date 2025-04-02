package com.example.lesantarosa.repository

import androidx.lifecycle.LiveData
import androidx.room.PrimaryKey
import com.example.lesantarosa.database.dao.ItemDao
import com.example.lesantarosa.database.dao.OrderDao
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.retrofit.webclient.ItemWebClient
import com.example.lesantarosa.retrofit.webclient.OrderWebClient

class OrderRepository(
    private val orderDao: OrderDao,
    private val webClient: OrderWebClient
) {

    suspend fun save(order: Order): Resource<Unit> {
        return try {
            webClient.save(order)
                .also { if (it.error == null) { orderDao.save(order) } }

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun remove(orderId: Long): Resource<Unit> {
        return try {
            webClient.remove(orderId)
                .also { if (it.error == null) { orderDao.remove(orderId) } }

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun getOrderById(orderId: Long): Order? {
        return orderDao.geOrderById(orderId)
    }

    fun searchOrders(search: String): LiveData<List<Order>> {
        return orderDao.searchOrders(search)
    }
}