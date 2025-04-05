package com.example.lesantarosa.models.enums

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.lesantarosa.R

enum class OrderStatus(val icon: Int, val displayName: Int) {
    PENDING(R.drawable.ic_clock, R.string.order_status_pending),
    CONFIRMED(R.drawable.ic_clock, R.string.order_status_confirmed),
    IN_PRODUCTION(R.drawable.ic_clock, R.string.order_status_in_production),
    OUT_FOR_DELIVERY(R.drawable.ic_clock, R.string.order_status_out_for_delivery),
    READY_FOR_PICKUP(R.drawable.ic_clock, R.string.order_status_ready_for_pickup);
}