package com.example.lesantarosa.models.data

import com.example.lesantarosa.models.entities.Item

class ProductItem(
    itemId: Long,
    title: String,
    description: String?,
    image: String?,
    price: Double,
    expirationDate: Long?,
    createdAt: Long,
    updatedAt: Long?,
    val weight: Int?,
    val packaging: String?,
    val discountPrice: Double?,
    val salesCount: Int,
): Item(itemId, title, description, image, price, expirationDate, createdAt, updatedAt) {

    companion object {

        fun createProduct(item: Item, weight: Int?, packaging: String?, discountPrice: Double?, salesCount: Int): ProductItem {
            return ProductItem(
                item.itemId,
                item.title,
                item.description,
                item.image,
                item.price,
                item.expirationDate,
                item.createdAt,
                item.updatedAt,
                weight,
                packaging,
                discountPrice,
                salesCount,
            )
        }
    }
}