package com.example.lesantarosa.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.entities.Stock
import com.example.lesantarosa.repository.ItemRepository
import com.example.lesantarosa.repository.StockRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class ManagementViewModel(
    private val itemRepository: ItemRepository,
    private val stockRepository: StockRepository,
    private val _itemId: Long?
): ViewModel() {

    val itemId = _itemId ?: Random.nextLong()

    fun saveItem(item: Item, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val resource = itemRepository.save(item)
            resource.error?.let { Log.i("", it) } ?: onSuccess()
        }
    }

    fun removeItem(onSuccess: () -> Unit) {
        _itemId?.let {
            viewModelScope.launch {
                val resource = itemRepository.remove(it)
                resource.error?.let { Log.i("", it) } ?: onSuccess()
            }
        }
    }

    fun getItem(onSuccess: (item: Item?) -> Unit) {
        viewModelScope.launch {
            val item = _itemId?.let { itemRepository.getItemById(it) }
            onSuccess(item)
        }
    }

    fun saveStock(stock: Stock, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val resource = stockRepository.save(stock)
            resource.error?.let { Log.i("", it) } ?: onSuccess()
        }
    }

    fun removeStock(onSuccess: () -> Unit) {
        _itemId?.let {
            viewModelScope.launch {
                val resource = stockRepository.remove(it)
                resource.error?.let { Log.i("", it) } ?: onSuccess()
            }
        }
    }

    fun getItemStock(onSuccess: (stock: Stock?) -> Unit) {
        viewModelScope.launch {
            val stock = _itemId?.let { stockRepository.getStockByItemId(it) }
            onSuccess(stock)
        }
    }
}