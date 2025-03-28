package com.example.lesantarosa.models.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "item",
    indices = [
        Index(value = ["itemId"]),
        Index(value = ["title"])
    ]
)
@Parcelize
open class Item(
    @PrimaryKey(autoGenerate = true)
    val itemId: Long = 0L,
    val title: String,
    val description: String?,
    val image: String?,
    val price: Double,
    val expirationDate: Long?,
    val createdAt: Long,
    val updatedAt: Long?
): Parcelable