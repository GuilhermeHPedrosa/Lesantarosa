package com.example.lesantarosa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lesantarosa.databinding.CardPaymentBinding
import com.example.lesantarosa.models.data.Payment
import com.example.lesantarosa.ui.fragment.formatPrice

class PaymentAdapter(
    private val context: Context,
    var setItemClick: (payment: Payment) -> Unit = {},
    var setRemoveClick: (payment: Payment) -> Unit = {}
): ListAdapter<Payment>() {

    inner class ViewHolder(private val binding: CardPaymentBinding): ListViewHolder<Payment>(binding.root) {

        private lateinit var payment: Payment

        init {
            itemView.setOnClickListener {
                if (::payment.isInitialized) { setItemClick(payment) }
            }
            binding.removePaymentButton.setOnClickListener {
                if (::payment.isInitialized) { setRemoveClick(payment) }
            }
        }

        override fun setViewInfo(value: Payment) {
            this.payment = value

            val title = binding.paymentMethodTitle
            title.text = payment.paymentMethod.name

            val totalPrice = binding.paymentTotalPrice
            totalPrice.text = payment.totalPrice.formatPrice()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = CardPaymentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}