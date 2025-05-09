package com.example.lesantarosa.repository

import androidx.lifecycle.LiveData
import com.example.lesantarosa.database.dao.StockDao
import com.example.lesantarosa.models.data.StockItem
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.models.entities.Stock
import com.example.lesantarosa.models.entities.StockMovement
import com.example.lesantarosa.retrofit.webclient.StockWebClient

class StockRepository(
    private val stockDao: StockDao,
    private val webClient: StockWebClient
) {

    // ===================== Stock =====================

    suspend fun save(stock: Stock): Resource<Unit> {
        return try {
//            webClient.save(stock)
//                .also { if (it.error == null) { stockDao.save(stock) } }

            stockDao.save(stock)
            Resource(null)

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun update(itemId: Long, newStock: Int): Resource<Unit> {
        return try {
//            webClient.update(itemId, newStock)
//                .also { if (it.error == null) { stockDao.update(itemId, newStock) } }

            stockDao.update(itemId, newStock)
            Resource(null)

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun remove(itemId: Long): Resource<Unit> {
        return try {
//            webClient.remove(itemId)
//                .also { if (it.error == null) { stockDao.remove(itemId) } }

            stockDao.remove(itemId)
            Resource(null)

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun getStockByItemId(itemId: Long): Stock? {
        return stockDao.getStockByItemId(itemId)
    }

    fun searchStocks(search: String, itemType: ItemType): LiveData<List<StockItem>> {
        return stockDao.searchStocks(search, itemType)
    }

    // ===================== Stock Movement =====================

    suspend fun saveMovement(stockMovement: StockMovement): Resource<Unit> {
        return try {
//            webClient.save(stock)
//                .also { if (it.error == null) { stockDao.save(stock) } }

            stockDao.saveMovement(stockMovement)
            Resource(null)

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    suspend fun removeMovement(stockMovementId: Long): Resource<Unit> {
        return try {
//            webClient.remove(stockId)
//                .also { if (it.error == null) { stockDao.remove(stockId) } }

            stockDao.removeMovement(stockMovementId)
            Resource(null)

        } catch (e: Exception) {
            Resource(null, e.message)
        }
    }

    fun getStockMovementsByItemId(itemId: Long): LiveData<List<StockMovement>> {
        return stockDao.getStockMovementsByItemId(itemId)
    }
}