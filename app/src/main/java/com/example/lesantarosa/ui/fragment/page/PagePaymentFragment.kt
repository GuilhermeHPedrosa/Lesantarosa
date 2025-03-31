package com.example.lesantarosa.ui.fragment.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.FragmentKeys.PRICE_REQUEST_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.PRICE_VALUE_KEY
import com.example.lesantarosa.databinding.FragmentPagePaymentBinding
import com.example.lesantarosa.models.data.Payment
import com.example.lesantarosa.models.data.VisualComponents
import com.example.lesantarosa.ui.fragment.bottomsheet.PaymentMethodsBottomSheetDialog
import com.example.lesantarosa.ui.fragment.component.PaymentsFragment
import com.example.lesantarosa.ui.fragment.formatPrice
import com.example.lesantarosa.ui.viewmodel.PaymentViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class PagePaymentFragment: PageFragment() {

    private lateinit var binding: FragmentPagePaymentBinding

    private lateinit var viewModel: PaymentViewModel

    private val paymentMethodsDialog by lazy {
        PaymentMethodsBottomSheetDialog(requireContext()) {
            viewModel.alterPaymentMethod(it)
            navigateToPriceFragment()
        }
    }

    private val finalPaymentPrice by lazy {
        navArgs<PagePaymentFragmentArgs>()
            .value
            .finalPrice
            .toDouble()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.defineVisualComponents(VisualComponents())

        viewModel = getViewModel<PaymentViewModel>{ parametersOf(finalPaymentPrice) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagePaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializePaymentFragment()

        setupFinalPaymentPrice()

        observePayments()
        observePaymentSelectionResult()

        handleShowPaymentMethodsButton()
        handleFinishOrderButton()
    }

    private fun initializePaymentFragment() {
        val paymentsFragment = PaymentsFragment()

        childFragmentManager
            .beginTransaction()
            .replace(R.id.payments_container, paymentsFragment)
            .commit()
    }

    private fun setupFinalPaymentPrice() {
        val finalPaymentPriceTextView = binding.finalPaymentPriceTextview
        finalPaymentPriceTextView.text = finalPaymentPrice.formatPrice()
    }

    private fun setupTotalRemainingPrice() {
        val totalRemainingPriceTextview = binding.totalRemainingPriceTextview
        totalRemainingPriceTextview.text = viewModel.totalRemainingPrice.formatPrice()
    }

    private fun observePaymentSelectionResult() {
        setFragmentResultListener(PRICE_REQUEST_KEY) { _, bundle ->
            val selectedPrice: Double = bundle.getDouble(PRICE_VALUE_KEY)
            viewModel.savePayment(selectedPrice)
        }
    }

    private fun observePayments() {
        viewModel.payments.observe(viewLifecycleOwner) {
            setupTotalRemainingPrice()
        }
    }

    private fun handleShowPaymentMethodsButton() {
        val showPaymentMethodsButton = binding.showPaymentMethodsButton
        showPaymentMethodsButton.setOnClickListener {
            paymentMethodsDialog.show()
        }
    }

    private fun handleFinishOrderButton() {
        val finishOrderButton = binding.finishOrderButton
        finishOrderButton.setOnClickListener {}
    }

    private fun navigateToPriceFragment() {
        val direction = PagePaymentFragmentDirections.actionPagePaymentToPriceDialog(0f, viewModel.totalRemainingPrice.toFloat())
        findNavController().navigate(direction)
    }
}