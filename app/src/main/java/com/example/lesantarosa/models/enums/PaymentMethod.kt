package com.example.lesantarosa.models.enums

import com.example.lesantarosa.R

enum class PaymentMethod(val icon: Int, val displayName: String) {
    CASH(R.drawable.ic_add, "Cash"),
    CREDIT_CARD(R.drawable.ic_add, "Credit"),
    DEBIT_CARD(R.drawable.ic_add, "Debit"),
    PIX(R.drawable.ic_add, "Pix"),
    BANK_TRANSFER(R.drawable.ic_add, "Bank Transfer"),
    DIGITAL_WALLET(R.drawable.ic_add, "Digital Wallet"),
    QR_CODE(R.drawable.ic_add, "QR Code")
}