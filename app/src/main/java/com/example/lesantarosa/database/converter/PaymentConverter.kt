package com.example.lesantarosa.database.converter

import androidx.room.TypeConverter
import com.example.lesantarosa.models.data.CartProduct
import com.example.lesantarosa.models.data.Payment
import com.example.lesantarosa.models.enums.PaymentMethod
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PaymentConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromPayments(values: List<Payment>?): String? {
        return gson.toJson(values)
    }

    @TypeConverter
    fun toPayments(values: String): List<Payment> {
        val listType = object : TypeToken<List<Payment>>() {}.type
        return gson.fromJson(values, listType)
    }
}