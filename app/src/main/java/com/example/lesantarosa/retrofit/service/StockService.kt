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

    @PUT("stock/{stockId}")
    suspend fun update(@Path("stockId") stockId: Long, @Body stock: Stock)

    @DELETE("stock/{stockId}")
    suspend fun remove(@Path("stockId") stockId: Long)

}