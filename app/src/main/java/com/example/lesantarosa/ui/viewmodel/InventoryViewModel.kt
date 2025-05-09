package com.example.lesantarosa.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.models.data.StockItem
import com.example.lesantarosa.models.entities.Category
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.entities.Stock
import com.example.lesantarosa.repository.CategoryRepository
import com.example.lesantarosa.repository.ItemRepository
import com.example.lesantarosa.repository.StockRepository
import kotlinx.coroutines.launch

class InventoryViewModel(
    private val itemRepository: ItemRepository,
    private val stockRepository: StockRepository,
    private val categoryRepository: CategoryRepository
): ViewModel() {

    fun saveCategory(category: Category) {
        viewModelScope.launch { categoryRepository.save(category) }
    }

    fun removeCategory(categoryId: Long) {
        viewModelScope.launch { categoryRepository.remove(categoryId) }
    }

    fun searchItems(search: String?): LiveData<List<Item>> {
        return itemRepository.searchItems(search ?: "")
    }

    fun searchStocks(search: String?): LiveData<List<StockItem>> {
        return stockRepository.searchStocks(search ?: "", itemType)
    }

    fun searchCategories(search: String?): LiveData<List<Category>> {
        return categoryRepository.searchCategories(search ?: "", itemType)
    }
}