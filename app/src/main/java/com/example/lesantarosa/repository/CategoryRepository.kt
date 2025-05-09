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
//            webClient.save(category)
//                .also { if (it.error == null) { categoryDao.save(category) } }

            categoryDao.save(category)
            Resource(Unit)

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun remove(categoryId: Long): Resource<Unit> {
        return try {
//            webClient.remove(categoryId)
//                .also { if (it.error == null) { categoryDao.remove(categoryId) } }

            categoryDao.remove(categoryId)
            Resource(Unit)

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    fun searchCategories(search: String, itemType: ItemType): LiveData<List<Category>> {
        return categoryDao.searchCategories(search, itemType)
    }
}