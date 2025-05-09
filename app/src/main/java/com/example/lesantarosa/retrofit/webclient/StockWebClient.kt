package com.example.lesantarosa.retrofit.webclient

import com.example.lesantarosa.database.utils.FragmentKeys.UNKNOWN_ERROR
import com.example.lesantarosa.models.entities.Stock
import com.example.lesantarosa.repository.Resource
import com.example.lesantarosa.retrofit.service.StockService

class StockWebClient(private val service: StockService) {

    suspend fun save(stock: Stock): Resource<Unit> {
        return try {
            val response = service.save(stock)
            Resource(response)
        } catch (e: Exception) {
            Resource(null, e.message ?: UNKNOWN_ERROR)
        }
    }

    suspend fun update(itemId: Long, newQuantity: Int): Resource<Unit> {
        return try {
            val response = service.update(itemId, newQuantity)
            Resource(response)
        } catch (e: Exception) {
            Resource(null, e.message ?: UNKNOWN_ERROR)
        }
    }

    suspend fun remove(itemId: Long): Resource<Unit> {
        return try {
            val response = service.remove(itemId)
            Resource(response)
        } catch (e: Exception) {
            Resource(null, e.message ?: UNKNOWN_ERROR)
        }
    }
}