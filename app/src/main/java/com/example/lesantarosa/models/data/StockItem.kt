package com.example.lesantarosa.models.data

import android.content.Context
import com.example.lesantarosa.models.enums.MeasureUnit

class StockItem(
    val itemId: Long,
    val title: String,
    val image: String,
    val currentStock: Int?,
    val reorderLevel: Int?,
    val measureUnit: MeasureUnit?
) {

    fun getCurrentStock(): String? {
        return if (currentStock == null || measureUnit == null) null
        else "$currentStock ${measureUnit.symbol}"
    }
}