package com.example.lesantarosa.ui.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesantarosa.models.data.Payment
import com.example.lesantarosa.models.enums.PaymentMethod
import com.example.lesantarosa.models.enums.PaymentMethod.CASH

class PaymentViewModel(
    private val finalPaymentPrice: Double
): ViewModel() {

    private val _payments = MutableLiveData<List<Payment>>()
    val payments: LiveData<List<Payment>> = _payments

    private var _paymentMethod: PaymentMethod = CASH

    val totalRemainingPrice: Double
        get() = finalPaymentPrice - (_payments.value?.sumOf { it.totalPrice } ?: 0.0)

    fun savePayment(price: Double) {
        val currentPayments = (_payments.value ?: emptyList()).toMutableList()
        currentPayments.add(Payment(_paymentMethod, price))

        _payments.postValue(currentPayments)
    }

    fun removePayment(payment: Payment) {
        val currentPayments = (_payments.value ?: emptyList()).toMutableList()
        currentPayments.remove(payment)

        _payments.postValue(currentPayments)
    }

    fun alterPaymentMethod(method: PaymentMethod) {
        _paymentMethod = method
    }
}