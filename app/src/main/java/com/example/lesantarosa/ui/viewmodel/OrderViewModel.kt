package com.example.lesantarosa.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.lesantarosa.models.data.OrdersSummary
import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.models.enums.OrderStatus
import com.example.lesantarosa.repository.OrderRepository

class OrderViewModel(
    private val orderRepository: OrderRepository
): ViewModel() {

    suspend fun getOrderById(orderId: Long): Order? {
        return orderRepository.getOrderById(orderId)
    }

    fun searchOrders(search: String? = null, status: OrderStatus? = null): LiveData<List<Order>> {
        return orderRepository.searchOrders(search ?: "", status)
    }

    fun getOrdersSummary(): LiveData<OrdersSummary> {
        return orderRepository.getOrdersSummary()
    }
}