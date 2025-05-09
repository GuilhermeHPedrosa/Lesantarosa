package com.example.lesantarosa.models.enums

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.example.lesantarosa.R

enum class OrderStatus(
    @StringRes val resId: Int,
    @DrawableRes val iconId: Int
) {
    PENDING(R.string.order_status_pending, R.drawable.ic_clock),
    CONFIRMED(R.string.order_status_confirmed, R.drawable.ic_clock),
    IN_PRODUCTION(R.string.order_status_in_production, R.drawable.ic_clock),
    OUT_FOR_DELIVERY(R.string.order_status_out_for_delivery, R.drawable.ic_clock),
    READY_FOR_PICKUP(R.string.order_status_ready_for_pickup, R.drawable.ic_clock);

    fun getStatusDisplay(context: Context): Pair<String, Drawable?> {
        val name = context.getString(resId)
        val icon = ContextCompat.getDrawable(context, iconId)
        return name to icon
    }
}