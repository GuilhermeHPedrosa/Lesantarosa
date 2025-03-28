package com.example.lesantarosa.retrofit.webclient

import com.example.lesantarosa.database.utils.FragmentKeys.UNKNOWN_ERROR
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.repository.Resource
import com.example.lesantarosa.retrofit.service.ItemService

class ItemWebClient(private val service: ItemService) {

    suspend fun save(item: Item): Resource<Unit> {
        return try {
            val response = service.save(item)
            Resource(response)
        } catch (e: Exception) {
            Resource(null, e.message ?: UNKNOWN_ERROR)
        }
    }

    suspend fun update(item: Item): Resource<Unit> {
       return try {
           val response = service.update(item)
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