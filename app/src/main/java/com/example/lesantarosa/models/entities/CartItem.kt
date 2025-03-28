package com.example.lesantarosa.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart",
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
class CartItem(
    @PrimaryKey(autoGenerate = true)
    val cartId: Long = 0L,
    val itemId: Long,
    val quantity: Int,
    val discountedPrice: Double?,
    val note: String?,
)