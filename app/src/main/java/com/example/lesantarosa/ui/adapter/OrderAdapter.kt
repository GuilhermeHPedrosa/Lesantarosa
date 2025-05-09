package com.example.lesantarosa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lesantarosa.databinding.CardOrderBinding
import com.example.lesantarosa.databinding.CardOrderHeaderBinding
import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.models.sealed.OrderItem
import com.example.lesantarosa.models.sealed.OrderItem.DateHeader
import com.example.lesantarosa.models.sealed.OrderItem.OrderEntry
import com.example.lesantarosa.ui.fragment.formatPrice

class OrderAdapter(
    private val context: Context,
    var setItemClick: (order: Order) -> Unit = {}
): ListAdapter<OrderItem>() {

    inner class HeaderViewHolder(private val binding: CardOrderHeaderBinding): ListViewHolder<OrderItem>(binding.root) {

        private lateinit var header: DateHeader

        override fun setViewInfo(value: OrderItem) {
            this.header = value as DateHeader

            val date = binding.headerDate
            date.text = header.formatDateHeader()

            val info = binding.headerInfo
            info.text = header.formatInfo()
        }
    }

    inner class OrderViewHolder(private val binding: CardOrderBinding): ListViewHolder<OrderItem>(binding.root) {

        private lateinit var order: Order

        init {
            itemView.setOnClickListener {
                if (::order.isInitialized) { setItemClick(order) }
            }
        }

        override fun setViewInfo(value: OrderItem) {
            this.order = (value as OrderEntry).order

            val finalAmount = binding.orderFinalAmount
            finalAmount.text = order.finalAmount.formatPrice()

            val info = binding.orderInfo
            info.text = order.products.joinToString(", ") { it.toString() }

            val title = binding.orderTitle
            title.text = order.title

            val payment = binding.orderPayment
            val majorPayment = order.payments.maxBy { it.totalPrice }

            payment.text = majorPayment.totalPrice.formatPrice()
            //payment.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, majorPayment.paymentMethod.getMethodDisplay(re)), null, null, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder<OrderItem> {
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = CardOrderHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            else -> {
                val binding = CardOrderBinding.inflate(inflater, parent, false)
                OrderViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (value(position)) {
            is DateHeader -> VIEW_TYPE_HEADER
            is OrderEntry -> VIEW_TYPE_ORDER
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ORDER = 1
    }
}