package com.example.lesantarosa.ui.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesantarosa.database.preferences.PaymentPreferences
import com.example.lesantarosa.models.data.CartProduct
import com.example.lesantarosa.models.data.Payment
import com.example.lesantarosa.models.enums.PaymentMethod
import com.example.lesantarosa.models.enums.PaymentMethod.CASH
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PaymentViewModel(private val finalPaymentPrice: Double): ViewModel() {

    private var paymentMethod: PaymentMethod = CASH

    val totalRemainingPrice: Double
        get() = finalPaymentPrice - PaymentPreferences.totalPaidPrice

    fun savePayment(context: Context, price: Double) {
        viewModelScope.launch {
            val payment = Payment(paymentMethod, price)
            PaymentPreferences.addPayment(context, payment)
        }
    }

    fun removePayment(context: Context, payment: Payment) {
        viewModelScope.launch {
            PaymentPreferences.removePayment(context, payment)
        }
    }

    fun alterPaymentMethod(method: PaymentMethod) {
        paymentMethod = method
    }
}