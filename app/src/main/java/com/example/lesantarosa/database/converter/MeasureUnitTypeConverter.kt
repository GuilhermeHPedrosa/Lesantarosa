package com.example.lesantarosa.database.converter

import androidx.room.TypeConverter
import com.example.lesantarosa.models.enums.MeasureUnit

class MeasureUnitTypeConverter {

    @TypeConverter
    fun fromMeasureUnit(unit: MeasureUnit): String {
        return unit.name
    }

    @TypeConverter
    fun toMeasureUnit(value: String): MeasureUnit {
        return MeasureUnit.valueOf(value)
    }
}