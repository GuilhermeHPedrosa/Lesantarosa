package com.example.lesantarosa.models.data

import com.example.lesantarosa.ui.fragment.formatPrice

data class CartSummary(
    val itemCount: Int,
    val totalAmount: Double,
    val totalDiscounts: Double,
) {

    val finalPrice: Double get() = totalAmount - totalDiscounts

    val formattedTotalAmount: String get() = totalAmount.formatPrice()
    val formattedDiscounts: String get() = totalDiscounts.formatPrice()
    val formattedFinalPrice: String get() = finalPrice.formatPrice()

}