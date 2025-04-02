package com.example.lesantarosa.database.converter

import androidx.room.TypeConverter
import com.example.lesantarosa.models.enums.MeasureUnit

class MeasureUnitConverter {

    @TypeConverter
    fun fromMeasureUnit(value: MeasureUnit): String {
        return value.name
    }

    @TypeConverter
    fun toMeasureUnit(value: String): MeasureUnit {
        return MeasureUnit.valueOf(value)
    }
}