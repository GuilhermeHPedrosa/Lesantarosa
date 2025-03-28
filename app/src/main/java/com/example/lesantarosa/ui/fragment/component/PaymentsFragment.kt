package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.models.data.Payment
import com.example.lesantarosa.ui.adapter.recyclerview.PaymentAdapter
import com.example.lesantarosa.ui.fragment.page.PagePaymentFragmentDirections
import com.example.lesantarosa.ui.viewmodel.PaymentViewModel

class PaymentsFragment: ListFragment<Payment>() {

    private val viewModel by viewModels<PaymentViewModel>(ownerProducer = { requireParentFragment() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateSource(viewModel.payments)
    }

    override fun initializeRecyclerView() {
        val adapter = PaymentAdapter(requireContext())

        adapter.setItemClick = {
            val direction = PagePaymentFragmentDirections.actionPagePaymentToPriceDialog(it.totalPrice.toFloat(), viewModel.totalRemainingPrice.toFloat())
            findNavController().navigate(direction)
        }

        adapter.setRemoveClick = {
            viewModel.removePayment(it)
        }

        this.adapter = adapter
    }

    override fun initializeFilterFragment() {}
}