package com.example.lesantarosa.models.data

import com.example.lesantarosa.ui.fragment.formatPrice

data class OrdersSummary(
    val totalQuantity: Int,
    val finalAmount: Double
) {

    override fun toString(): String {
        return "${finalAmount.formatPrice()} em $totalQuantity pedidos"
    }
}