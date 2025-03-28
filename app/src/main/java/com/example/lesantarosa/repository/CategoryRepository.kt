package com.example.lesantarosa.repository

import androidx.lifecycle.LiveData
import com.example.lesantarosa.database.dao.CategoryDao
import com.example.lesantarosa.models.entities.Category
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.retrofit.webclient.CategoryWebClient

class CategoryRepository(
    private val categoryDao: CategoryDao,
    private val webClient: CategoryWebClient
) {

    suspend fun save(category: Category): Resource<Unit> {
        return try {
            webClient.save(category)
                .also { if (it.error == null) { categoryDao.save(category) } }

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun update(category: Category): Resource<Unit> {
        return try {
            webClient.update(category)
                .also { if (it.error == null) { categoryDao.save(category) } }

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun remove(categoryId: Long): Resource<Unit> {
        return try {
            webClient.remove(categoryId)
                .also { if (it.error == null) { categoryDao.remove(categoryId) } }

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun getCategoryById(categoryId: Long): Category? {
        return categoryDao.getCategoryById(categoryId)
    }

    fun searchCategories(search: String, itemType: ItemType): LiveData<List<Category>> {
        return categoryDao.searchCategories(search, itemType)
    }
}