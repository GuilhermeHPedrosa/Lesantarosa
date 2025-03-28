package com.example.lesantarosa.ui.fragment.bottomsheet

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesantarosa.database.preferences.CartPreferences
import com.example.lesantarosa.databinding.DialogDiscountBinding
import com.example.lesantarosa.ui.adapter.textwatcher.PercentTextWatcher
import com.example.lesantarosa.ui.adapter.textwatcher.PriceTextWatcher
import com.example.lesantarosa.ui.fragment.formatPrice
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class DiscountBottomSheetDialogFragment: BottomSheetDialogFragment() {

    private lateinit var binding: DialogDiscountBinding

    private lateinit var priceTextWatcher: PriceTextWatcher
    private lateinit var percentTextWatcher: PercentTextWatcher

    private val args by lazy { navArgs<DiscountBottomSheetDialogFragmentArgs>().value }

    private val totalPrice by lazy { args.totalPrice.toDouble() }
    private val discountedPrice by lazy { MutableLiveData(args.actualDiscount.toDouble()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogDiscountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializePriceTextWatcher()
        initializePercentTextWatcher()

        setupPriceTitle()
        setupFixedDiscount()
        setupPercentageDiscount()

        observeDiscountedPrice()

        handleSaveButton()
    }

    private fun initializePriceTextWatcher() {
        val textWatcher = PriceTextWatcher(binding.fixedDiscountEditText, totalPrice) { discountedPrice.value = it }
        priceTextWatcher = textWatcher
    }

    private fun initializePercentTextWatcher() {
        val textWatcher = PercentTextWatcher(binding.percentageEditText, totalPrice) { discountedPrice.value = it }
        percentTextWatcher = textWatcher
    }

    private fun setupFixedDiscount() {
        val editText = binding.fixedDiscountEditText
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER)

        editText.addTextChangedListener(priceTextWatcher)
    }

    private fun setupPercentageDiscount() {
        val editText = binding.percentageEditText
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER)

        editText.addTextChangedListener(percentTextWatcher)
    }

    private fun setupPriceTitle() {
        activity?.let {
            (it as? AppCompatActivity)?.supportActionBar?.title = "Discount on R$ $totalPrice"
        }
    }

    private fun setupFinalPriceValue(finalPrice: Double) {
        val finalPriceTextView = binding.finalPriceTextView
        finalPriceTextView.text = (totalPrice - finalPrice).formatPrice()
    }

    private fun observeDiscountedPrice() {
        discountedPrice.observe(viewLifecycleOwner) {
            setupFinalPriceValue(it)

            priceTextWatcher.updatePrice(it)
            percentTextWatcher.updateCurrency(it)
        }
    }

    private fun handleSaveButton() {
        val saveDiscountButton = binding.saveDiscountButton
        saveDiscountButton.setOnClickListener {
            lifecycleScope.launch {
                CartPreferences.updateDiscount(requireContext(), discountedPrice.value)
                findNavController().navigateUp()
            }
        }
    }
}