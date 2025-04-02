package com.example.lesantarosa.retrofit.service

import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.models.entities.Stock
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderService {

    @POST("order")
    suspend fun save(@Body order: Order)

    @PUT("order/{orderId}")
    suspend fun update(@Path("orderId") orderId: Long, @Body order: Order)

    @DELETE("order/{orderId}")
    suspend fun remove(@Path("orderId") orderId: Long)

}