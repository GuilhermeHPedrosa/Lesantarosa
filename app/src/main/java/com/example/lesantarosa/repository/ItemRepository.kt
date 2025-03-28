package com.example.lesantarosa.repository

import androidx.lifecycle.LiveData
import com.example.lesantarosa.database.dao.ItemDao
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.retrofit.webclient.ItemWebClient

class ItemRepository(
    private val itemDao: ItemDao,
    private val webClient: ItemWebClient
) {

    suspend fun save(item: Item): Resource<Unit> {
        return try {
            webClient.save(item)
                .also { if (it.error == null) { itemDao.save(item) } }

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun remove(itemId: Long): Resource<Unit> {
        return try {
            webClient.remove(itemId)
                .also { if (it.error == null) { itemDao.remove(itemId) } }

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun getItemById(itemId: Long): Item? {
        return itemDao.getItemById(itemId)
    }

    fun searchItems(search: String): LiveData<List<Item>> {
        return itemDao.searchItems(search)
    }
}