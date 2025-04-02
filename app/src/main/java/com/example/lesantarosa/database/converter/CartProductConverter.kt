package com.example.lesantarosa.database.converter

import androidx.room.TypeConverter
import com.example.lesantarosa.models.data.CartProduct
import com.example.lesantarosa.models.enums.Difficulty
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CartProductConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromCartProducts(values: List<CartProduct>?): String? {
        return gson.toJson(values)
    }

    @TypeConverter
    fun toCartProducts(values: String): List<CartProduct> {
        val listType = object : TypeToken<List<CartProduct>>() {}.type
        return gson.fromJson(values, listType)
    }
}