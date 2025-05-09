package com.example.lesantarosa.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesantarosa.models.data.CartProduct
import com.example.lesantarosa.repository.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
): ViewModel() {

    val cartSummary = cartRepository.getCartSummary()

    val totalPrice get() = cartSummary.value?.totalAmount ?: 0.0
    val finalPrice get() = cartSummary.value?.finalPrice ?: 0.0

    fun clearCart() {
        viewModelScope.launch { cartRepository.removeAll() }
    }

    fun getCartProducts(): LiveData<List<CartProduct>> {
        return cartRepository.getCartProducts()
    }
}