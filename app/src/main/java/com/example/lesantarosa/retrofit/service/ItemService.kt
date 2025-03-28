package com.example.lesantarosa.retrofit.service

import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.repository.Resource
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface ItemService {

    @POST("item")
    suspend fun saveItem(@Body item: Item): Resource<Unit>

    @DELETE("item/{id}")
    suspend fun removeItem(@Path("id") id: Long): Unit

    suspend fun save(item: Item)

    suspend fun update(item: Item)

    suspend fun remove(itemId: Long)

}