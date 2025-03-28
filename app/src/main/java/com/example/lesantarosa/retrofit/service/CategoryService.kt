package com.example.lesantarosa.retrofit.service

import com.example.lesantarosa.models.entities.Category
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryService {

    @POST("category")
    suspend fun save(@Body category: Category)

    @PUT("category/{categoryId}")
    suspend fun update(@Path("categoryId") categoryId: Long, @Body category: Category)

    @DELETE("category/{categoryId}")
    suspend fun remove(@Path("categoryId") categoryId: Long)

}