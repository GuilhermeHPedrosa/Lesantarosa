package com.example.lesantarosa.models.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.lesantarosa.models.enums.ItemType

@Entity(
    tableName = "item",
    indices = [
        Index(value = ["itemId"]),
        Index(value = ["title"])
    ]
)
open class Item(
    @PrimaryKey(autoGenerate = true)
    val itemId: Long = 0L,
    val title: String,
    val description: String?,
    val image: String?,
    val price: Double,
    val expirationDate: Long?,
    val createdAt: Long,
    val updatedAt: Long?,
    val itemType: ItemType,
)