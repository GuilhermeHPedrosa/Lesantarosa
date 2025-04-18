package com.example.lesantarosa.models.data

import com.example.lesantarosa.ui.fragment.formatPrice

data class SaleSummary(
    val totalQuantity: Int,
    val totalPrice: Double
) {

    override fun toString(): String {
        return "$totalQuantity items -> ${totalPrice.formatPrice()}"
    }
}