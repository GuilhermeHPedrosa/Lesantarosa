package com.example.lesantarosa.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesantarosa.models.entities.CartItem
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.models.data.SaleSummary
import com.example.lesantarosa.repository.CartRepository
import kotlinx.coroutines.launch

class SaleViewModel(
    private val cartRepository: CartRepository,
): ViewModel() {

    private var selectedSaleQuantity = 1

    val saleSummary: LiveData<SaleSummary>
        get() = cartRepository.getSaleSummary()

    fun addToCart(productItem: ProductItem) {
        viewModelScope.launch {
            cartRepository.addOrUpdate(createCartEntity(productItem))
            selectedSaleQuantity = 1
        }
    }

    fun updateSaleQuantity(quantity: Int) {
        selectedSaleQuantity = quantity.takeIf { it > 0 } ?: 1
    }

    fun searchProductsForSale(search: String?): LiveData<List<ProductItem>> {
        return cartRepository.searchProductsForSale(search ?: "")
    }

    private fun createCartEntity(productItem: ProductItem): CartItem {
        return CartItem(0L, productItem.itemId, selectedSaleQuantity, null, null)
    }
}