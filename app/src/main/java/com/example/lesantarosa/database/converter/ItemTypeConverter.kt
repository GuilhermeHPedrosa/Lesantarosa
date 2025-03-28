package com.example.lesantarosa.database.converter

import androidx.room.TypeConverter
import com.example.lesantarosa.models.enums.ItemType

class ItemTypeConverter {

    @TypeConverter
    fun fromItemType(value: ItemType): String {
        return value.name
    }

    @TypeConverter
    fun toItemType(value: String): ItemType {
        return ItemType.valueOf(value)
    }
}