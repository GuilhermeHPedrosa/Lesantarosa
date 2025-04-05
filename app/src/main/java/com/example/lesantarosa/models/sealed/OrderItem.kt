package com.example.lesantarosa.models.sealed

import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.ui.fragment.formatPrice
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

sealed class OrderItem {

    data class DateHeader(val date: LocalDate, val totalAmount: Double, val orderCount: Int): OrderItem() {

        fun formatDateHeader(): String {
            val formatter = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMM 'de' yyyy")
            return formatter.format(date).replace(".", "")
        }

        fun formatInfo(): String {
            val orderCountText = if (orderCount > 1) "pedidos" else "pedido"
            return "$orderCount $orderCountText, ${totalAmount.formatPrice()}"
        }
    }

    data class OrderEntry(val order: Order): OrderItem()
}