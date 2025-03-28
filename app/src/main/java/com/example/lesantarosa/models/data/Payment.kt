package com.example.lesantarosa.models.data

import com.example.lesantarosa.models.enums.PaymentMethod

data class Payment(
    val paymentMethod: PaymentMethod,
    val totalPrice: Double
)