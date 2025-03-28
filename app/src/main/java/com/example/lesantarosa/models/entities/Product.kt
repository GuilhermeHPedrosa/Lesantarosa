package com.example.lesantarosa.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "product",
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
class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0L,
    val itemId: Long,
    val weight: Int?,
    val packaging: String?,
    val discountPrice: Double?,
    val salesCount: Int = 0
)