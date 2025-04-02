package com.example.lesantarosa.retrofit.webclient

import com.example.lesantarosa.database.utils.FragmentKeys.UNKNOWN_ERROR
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.repository.Resource
import com.example.lesantarosa.retrofit.service.ItemService
import com.example.lesantarosa.retrofit.service.OrderService

class OrderWebClient(private val service: OrderService) {

    suspend fun save(order: Order): Resource<Unit> {
        return try {
            val response = service.save(order)
            Resource(response)
        } catch (e: Exception) {
            Resource(null, e.message ?: UNKNOWN_ERROR)
        }
    }

    suspend fun update(order: Order): Resource<Unit> {
        return try {
            val response = service.update(order.orderId, order)
            Resource(response)
        } catch (e: Exception) {
            Resource(null, e.message ?: UNKNOWN_ERROR)
        }
    }

    suspend fun remove(itemId: Long): Resource<Unit> {
        return try {
            val response = service.remove(itemId)
            Resource(response)
        } catch (e: Exception) {
            Resource(null, e.message ?: UNKNOWN_ERROR)
        }
    }
}