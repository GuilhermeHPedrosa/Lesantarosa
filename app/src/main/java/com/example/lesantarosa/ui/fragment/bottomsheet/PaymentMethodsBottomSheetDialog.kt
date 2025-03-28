package com.example.lesantarosa.ui.fragment.bottomsheet

import android.content.Context
import android.view.LayoutInflater
import com.example.lesantarosa.databinding.ButtonPaymentMethodBinding
import com.example.lesantarosa.databinding.DialogPaymentMethodsBinding
import com.example.lesantarosa.models.enums.PaymentMethod
import com.google.android.material.bottomsheet.BottomSheetDialog

class PaymentMethodsBottomSheetDialog(
    private val context: Context,
    private val onMethodSelected: (PaymentMethod) -> Unit
): BottomSheetDialog(context) {

    private val binding = DialogPaymentMethodsBinding.inflate(layoutInflater)

    private val paymentGrid by lazy { binding.paymentMethodsGridLayout }

    init {
        setContentView(binding.root)

        setupPaymentMethodButtons()
        setupCloseButton()
    }

    private fun setupPaymentMethodButtons() {
        val inflater = LayoutInflater.from(context)
        PaymentMethod.entries.forEach { method ->
            val paymentButton = ButtonPaymentMethodBinding.inflate(inflater, paymentGrid, false).paymentButton

            paymentButton.text = method.displayName
            paymentButton.setOnClickListener { onMethodSelected(method) ; dismiss() }

            paymentGrid.addView(paymentButton)
        }
    }

    private fun setupCloseButton() {
        binding.confirmButton.setOnClickListener { dismiss() }
    }
}