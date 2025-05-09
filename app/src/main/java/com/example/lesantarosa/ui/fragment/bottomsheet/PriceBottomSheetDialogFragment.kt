package com.example.lesantarosa.ui.fragment.bottomsheet

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesantarosa.database.utils.FragmentKeys.PRICE_REQUEST_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.PRICE_VALUE_KEY
import com.example.lesantarosa.databinding.ButtonNumpadBinding
import com.example.lesantarosa.databinding.DialogPriceBinding
import com.example.lesantarosa.ui.watcher.PriceTextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PriceBottomSheetDialogFragment: BottomSheetDialogFragment() {

    private lateinit var binding: DialogPriceBinding

    private lateinit var priceTextWatcher: PriceTextWatcher

    private val inputLayout by lazy { binding.priceInputLayout }
    private val numpadLayout by lazy { binding.numpadGridLayout }

    private val args by lazy { navArgs<PriceBottomSheetDialogFragmentArgs>().value }

    private var selectedPrice: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPriceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeSelectedPrice()
        initializePriceTextWatcher()

        setupInputTextWatcher()
        setupNumpad()

        handleSaveButton()
    }

    private fun initializeSelectedPrice() {
        val initialPrice = args.initialPrice.toDouble()
        selectedPrice = initialPrice
    }

    private fun initializePriceTextWatcher() {
        val priceLimit = args.priceLimit.toDouble()

        priceTextWatcher = PriceTextWatcher(binding.priceInputLayout, priceLimit, selectedPrice) {
            selectedPrice = it
        }
    }

    private fun setupInputTextWatcher() {
        inputLayout.addTextChangedListener(priceTextWatcher)
    }

    private fun setupNumpad() {
        val numpadNumbers = listOf(
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "", "0", "⌫"
        )

        numpadNumbers.forEach { char ->
            val numpadButton = inflateNumpadButton(char)
            numpadLayout.addView(numpadButton)
        }
    }

    private fun inflateNumpadButton(char: String): Button {
        val layoutInflater = LayoutInflater.from(requireContext())
        val layout = ButtonNumpadBinding.inflate(layoutInflater, numpadLayout, false).numpadButton

        layout.text = char
        layout.setOnClickListener { handleNumpadClick(char) }

        return layout
    }

    private fun handleNumpadClick(char: String) {
        val keyCode = getKeyCode(char)
        simulateKeyEvent(keyCode)
    }

    private fun getKeyCode(char: String): Int {
        return when (char) {
            "⌫" -> KeyEvent.KEYCODE_DEL
            in "0".."9" -> KeyEvent.KEYCODE_0 + (char[0] - '0')
            else -> KeyEvent.KEYCODE_UNKNOWN
        }
    }

    private fun simulateKeyEvent(keyCode: Int) {
        inputLayout.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, keyCode))
        inputLayout.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, keyCode))
    }

    private fun handleSaveButton() {
        val saveButton = binding.savePriceButton
        saveButton.setOnClickListener {
            setFragmentResult(PRICE_REQUEST_KEY, bundleOf(PRICE_VALUE_KEY to selectedPrice))
            findNavController().navigateUp()
        }
    }
}