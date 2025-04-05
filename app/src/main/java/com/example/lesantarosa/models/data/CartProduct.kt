package com.example.lesantarosa.models.data

data class CartProduct(
    val title: String,
    val image: String,
    val price: Double,
    val quantity: Int,
    val discountedPrice: Double?,
    val note: String?
) {

    override fun toString(): String {
        return "${quantity}x $title"
    }
}