package com.example.lesantarosa.models.enums

import com.example.lesantarosa.R

enum class PaymentMethod(val icon: Int, val displayName: String) {
    CASH(R.drawable.ic_profile, "Cash"),
    CREDIT_CARD(R.drawable.ic_profile, "Credit"),
    DEBIT_CARD(R.drawable.ic_profile, "Debit"),
    PIX(R.drawable.ic_profile, "Pix"),
    BANK_TRANSFER(R.drawable.ic_profile, "Bank Transfer"),
    DIGITAL_WALLET(R.drawable.ic_profile, "Digital Wallet"),
    QR_CODE(R.drawable.ic_profile, "QR Code")
}