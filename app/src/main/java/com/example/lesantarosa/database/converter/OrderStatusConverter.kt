package com.example.lesantarosa.database.converter

import androidx.room.TypeConverter
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.models.enums.OrderStatus

class OrderStatusConverter {

    @TypeConverter
    fun fromOrderStatus(value: OrderStatus): String {
        return value.name
    }

    @TypeConverter
    fun toOrderStatus(value: String): OrderStatus {
        return OrderStatus.valueOf(value)
    }
}