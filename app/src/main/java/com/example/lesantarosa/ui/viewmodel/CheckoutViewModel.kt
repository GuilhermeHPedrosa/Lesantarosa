package com.example.lesantarosa.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesantarosa.database.preferences.CartPreferences
import com.example.lesantarosa.database.preferences.PaymentPreferences
import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.repository.CartRepository
import com.example.lesantarosa.repository.OrderRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
): ViewModel() {

    private fun clearPurchase(context: Context) {
        viewModelScope.launch {
            cartRepository.removeAll()
            CartPreferences.updateNote(context, null)
            PaymentPreferences.removePayment(context, null)
        }
    }

    fun finishOrder(context: Context, title: String, customerContact: String, deadline: Int) {
        viewModelScope.launch {
            val cartProducts = cartRepository.finalizeOrder()

            val totalAmount = cartProducts.sumOf { it.price }
            val discountAmount = cartProducts.sumOf { it.discountedPrice ?: 0.0 }
            val finalAmount = totalAmount - discountAmount
            val totalItems = cartProducts.size

            val note = CartPreferences.getCartNote(context).first()
            val payments = PaymentPreferences.getPayments(context).first()

            val order = Order(0L, title, cartProducts, totalAmount, discountAmount, finalAmount, totalItems, note, payments, deadline, customerContact)

            val resource = orderRepository.save(order)
            resource.error.takeIf { it != null }?.let { throw Exception(it) } ?: clearPurchase(context)
        }
    }
}