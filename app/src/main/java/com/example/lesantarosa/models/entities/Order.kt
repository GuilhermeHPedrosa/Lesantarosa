package com.example.lesantarosa.models.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.lesantarosa.models.data.CartProduct
import com.example.lesantarosa.models.data.Payment
import com.example.lesantarosa.models.enums.OrderStatus

@Entity(
    tableName = "orders",
    indices = [
        Index(value = ["orderId"], unique = true)
    ]
)
class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0L,
    val title: String,
    val products: List<CartProduct>,
    val totalAmount: Double,
    val discountAmount: Double,
    val finalAmount: Double,
    val totalItems: Int,
    val note: String,
    val payments: List<Payment>,
    val deadline: Long,
    val customerContact: String,
    val orderStatus: OrderStatus = OrderStatus.IN_PRODUCTION,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)