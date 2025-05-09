package com.example.lesantarosa.models.enums

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.example.lesantarosa.R

enum class PaymentMethod(
    @StringRes private val resId: Int,
    @DrawableRes private val iconId: Int
) {
    CASH(R.string.payment_method_cash, R.drawable.ic_cash),
    DEBIT_CARD(R.string.payment_method_debit_card, R.drawable.ic_debit),
    CREDIT_CARD(R.string.payment_method_credit_card, R.drawable.ic_credit),
    PIX(R.string.payment_method_pix, R.drawable.ic_pix),
    DIGITAL_WALLET(R.string.payment_method_digital_wallet, R.drawable.ic_wallet);

    fun getMethodDisplay(context: Context): Pair<String, Drawable?> {
        val name = context.getString(resId)
        val icon = ContextCompat.getDrawable(context, iconId)
        return name to icon
    }
}