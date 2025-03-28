package com.example.lesantarosa.retrofit.webclient

import com.example.lesantarosa.database.utils.FragmentKeys.UNKNOWN_ERROR
import com.example.lesantarosa.models.entities.Category
import com.example.lesantarosa.repository.Resource
import com.example.lesantarosa.retrofit.service.CategoryService

class CategoryWebClient(private val service: CategoryService) {

    suspend fun save(category: Category): Resource<Unit> {
        return try {
            val response = service.save(category)
            Resource(response)
        } catch (e: Exception) {
            Resource(null, e.message ?: UNKNOWN_ERROR)
        }
    }

    suspend fun update(category: Category): Resource<Unit> {
        return try {
            val response = service.update(category.categoryId, category)
            Resource(response)
        } catch (e: Exception) {
            Resource(null, e.message ?: UNKNOWN_ERROR)
        }
    }

    suspend fun remove(categoryId: Long): Resource<Unit> {
        return try {
            val response = service.remove(categoryId)
            Resource(response)
        } catch (e: Exception) {
            Resource(null, e.message ?: UNKNOWN_ERROR)
        }
    }
}