package com.example.lesantarosa.retrofit.service

import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.models.entities.Item
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService: ItemService {

    @POST("product")
    suspend fun saveProduct(@Body productItem: ProductItem)

    @PUT("product/{itemId}")
    suspend fun updateProduct(@Path("itemId") itemId: Long, @Body productItem: ProductItem)

    @DELETE("product/{itemId}")
    suspend fun removeProduct(@Path("itemId") itemId: Long)

    override suspend fun save(item: Item) {
        if (item !is ProductItem) throw IllegalArgumentException("Invalid Product")

        saveProduct(item)
    }

    override suspend fun update(item: Item) {
        if (item !is ProductItem) throw IllegalArgumentException("Invalid Product")

        updateProduct(item.itemId, item)
    }

    override suspend fun remove(itemId: Long) {
        removeProduct(itemId)
    }
}