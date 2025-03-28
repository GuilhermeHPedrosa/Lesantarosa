package com.example.lesantarosa.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesantarosa.models.data.Payment
import com.example.lesantarosa.models.enums.PaymentMethod

class PaymentViewModel(
    private val finalPaymentPrice: Double
): ViewModel() {

    private val _payments = MutableLiveData<List<Payment>>()
    val payments: LiveData<List<Payment>> = _payments

    private var _paymentMethod: PaymentMethod? = null
    val paymentMethod: PaymentMethod
        get() = _paymentMethod ?: throw IllegalArgumentException("")

    val totalRemainingPrice: Double
        get() = (finalPaymentPrice - (_payments.value?.sumOf { it.totalPrice } ?: 0.0))

    fun savePayment(payment: Payment) {
        val currentPayments = (_payments.value ?: emptyList()).toMutableList()
        currentPayments.add(payment)

        _payments.postValue(currentPayments)
    }

    fun removePayment(payment: Payment) {
        val currentPayments = (_payments.value ?: emptyList()).toMutableList()
        currentPayments.remove(payment)

        _payments.postValue(currentPayments)
    }

    fun alterPaymentMethod(method: PaymentMethod?) {
        _paymentMethod = method
    }
}