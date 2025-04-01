package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.database.preferences.CartPreferences
import com.example.lesantarosa.database.preferences.PaymentPreferences
import com.example.lesantarosa.models.data.CartProduct
import com.example.lesantarosa.models.data.Payment
import com.example.lesantarosa.ui.adapter.recyclerview.PaymentAdapter
import com.example.lesantarosa.ui.fragment.page.PagePaymentFragmentDirections
import com.example.lesantarosa.ui.viewmodel.PaymentViewModel

class PaymentsFragment: ListFragment<Payment>() {

    private val viewModel by viewModels<PaymentViewModel>(ownerProducer = { requireParentFragment() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateSource(PaymentPreferences.getPayments(requireContext()).asLiveData())
    }

    override fun initializeRecyclerView() {
        val adapter = PaymentAdapter(requireContext())

        adapter.setItemClick = {
            val direction = PagePaymentFragmentDirections.actionPagePaymentToPriceDialog(it.totalPrice.toFloat(), viewModel.totalRemainingPrice.toFloat())
            findNavController().navigate(direction)
        }

        adapter.setRemoveClick = {
            viewModel.removePayment(requireContext(), it)
        }

        this.adapter = adapter
    }

    override fun initializeFilterFragment() {}
}