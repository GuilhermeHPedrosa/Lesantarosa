package com.example.lesantarosa.repository

import androidx.lifecycle.LiveData
import com.example.lesantarosa.database.dao.StockDao
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.models.entities.Stock
import com.example.lesantarosa.retrofit.webclient.StockWebClient

class StockRepository(
    private val stockDao: StockDao,
    private val webClient: StockWebClient
) {

    suspend fun save(stock: Stock): Resource<Unit> {
        return try {
            webClient.save(stock)
                .also { if (it.error == null) { stockDao.save(stock) } }

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun update(stock: Stock): Resource<Unit> {
        return try {
            webClient.update(stock)
                .also { if (it.error == null) { stockDao.save(stock) } }

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun remove(stockId: Long): Resource<Unit> {
        return try {
            webClient.remove(stockId)
                .also { if (it.error == null) { stockDao.remove(stockId) } }

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun getStockByItemId(itemId: Long): Stock? {
        return stockDao.getStockByItemId(itemId)
    }

    fun searchStocks(search: String, itemType: ItemType): LiveData<List<Stock>> {
        return stockDao.searchStocks(search, itemType)
    }
}