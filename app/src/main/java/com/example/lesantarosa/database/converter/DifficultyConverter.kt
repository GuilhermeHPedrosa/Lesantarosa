package com.example.lesantarosa.database.converter

import androidx.room.TypeConverter
import com.example.lesantarosa.models.enums.Difficulty

class DifficultyConverter {

    @TypeConverter
    fun fromDifficulty(value: Difficulty): String {
        return value.name
    }

    @TypeConverter
    fun toDifficulty(value: String): Difficulty {
        return Difficulty.valueOf(value)
    }
}