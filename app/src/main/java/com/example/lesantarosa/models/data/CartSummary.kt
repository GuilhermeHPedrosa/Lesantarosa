package com.example.lesantarosa.models.data

data class CartSummary(
    val totalQuantity: Int,
    val totalPrice: Double,
    val totalDiscounts: Double,
    val finalPrice: Double
)