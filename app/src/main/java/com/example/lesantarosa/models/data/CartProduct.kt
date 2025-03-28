package com.example.lesantarosa.models.data

import androidx.room.Embedded

class CartProduct(
    @Embedded
    val productItem: ProductItem,
    val quantity: Int,
    val discountedPrice: Double?,
    val note: String?
)