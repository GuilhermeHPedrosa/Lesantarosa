package com.example.lesantarosa.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.lesantarosa.database.converter.DifficultyConverter
import com.example.lesantarosa.models.enums.Difficulty

@Entity(
    tableName = "recipe",
    foreignKeys = [
        ForeignKey(
            entity = Item::class,
            parentColumns = ["itemId"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["itemId"],
            unique = true
        )
    ]
)
@TypeConverters(DifficultyConverter::class)
class Recipe(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long = 0L,
    val itemId: Long,
    val yield: Int?,
    val servings: Int?,
    val preparationTime: Int?,
    val difficulty: Difficulty?
)