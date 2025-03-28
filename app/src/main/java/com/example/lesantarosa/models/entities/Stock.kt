package com.example.lesantarosa.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.models.enums.MeasureUnit

@Entity(
    tableName = "stock",
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
class Stock(
    @PrimaryKey(autoGenerate = true)
    val stockId: Long = 0L,
    val itemId: Long,
    val minStock: Int,
    val maxStock: Int,
    val currentStock: Int,
    val reorderLevel: Int,
    val measureUnit: MeasureUnit,
    val itemType: ItemType
)