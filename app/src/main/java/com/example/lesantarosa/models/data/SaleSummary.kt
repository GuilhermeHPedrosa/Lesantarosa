package com.example.lesantarosa.models.data

import android.content.Context
import com.example.lesantarosa.ui.fragment.formatPrice

data class SaleSummary(
    val itemCount: Int,
    val totalAmount: Double
) {

    fun getSummary(context: Context): String {
        return if(itemCount < 1) "Empty Cart"
        else "$itemCount items -> ${totalAmount.formatPrice()}"
    }
}