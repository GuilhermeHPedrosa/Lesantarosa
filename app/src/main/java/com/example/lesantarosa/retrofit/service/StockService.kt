package com.example.lesantarosa.retrofit.service

import com.example.lesantarosa.models.entities.Stock
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StockService {

    @POST("stock")
    suspend fun save(@Body stock: Stock)

    @PUT("stock/{itemId}")
    suspend fun update(@Path("itemId") itemId: Long, @Body newQuantity: Int)

    @DELETE("stock/{itemId}")
    suspend fun remove(@Path("itemId") itemId: Long)

}