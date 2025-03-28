package com.example.lesantarosa.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lesantarosa.models.enums.ItemType

@Entity(tableName = "category")
class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long = 0L,
    val title: String,
    val itemType: ItemType
)