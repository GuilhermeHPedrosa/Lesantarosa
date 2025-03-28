package com.example.lesantarosa.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredient",
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
class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Long = 0L,
    val itemId: Long,
    val brand: String?,
    val supplier: String?,
    val nutritionalInfo: String?
)