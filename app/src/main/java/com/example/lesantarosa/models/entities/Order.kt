package com.example.lesantarosa.models.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.lesantarosa.database.converter.CartProductConverter
import com.example.lesantarosa.database.converter.PaymentConverter
import com.example.lesantarosa.models.data.CartProduct
import com.example.lesantarosa.models.data.Payment
import com.example.lesantarosa.models.enums.OrderStatus

@Entity(
    tableName = "order",
    indices = [
        Index(value = ["orderId"], unique = true)
    ]
)
@TypeConverters(CartProductConverter::class, PaymentConverter::class)
class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0L,
    val title: String,
    val products: List<CartProduct>,
    val totalAmount: Double,
    val discountAmount: Double,
    val finalAmount: Double,
    val totalQuantity: Int,
    val note: String,
    val payments: List<Payment>,
    val deadline: Int,
    val customerContact: String,
    val orderStatus: OrderStatus = OrderStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)