package com.example.lesantarosa.ui.fragment.bottomsheet

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesantarosa.database.utils.FragmentKeys.QUANTITY_REQUEST_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.QUANTITY_VALUE_KEY
import com.example.lesantarosa.databinding.ButtonNumpadBinding
import com.example.lesantarosa.databinding.DialogQuantityBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class QuantityBottomSheetDialogFragment: BottomSheetDialogFragment() {

    private lateinit var binding: DialogQuantityBinding

    private val inputLayout: EditText by lazy { binding.quantityInputLayout }
    private val numpadLayout: GridLayout by lazy { binding.numpadGridLayout }

    private val args by lazy { navArgs<QuantityBottomSheetDialogFragmentArgs>().value }

    private var selectedQuantity = args.initialQuantity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogQuantityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupInputLayoutTextWatcher()
        setupNumpadButtons()

        handleSaveButton()
    }

//    private fun initializeSelectedQuantity() {
//        val initialQuantity = args.initialQuantity
//        selectedQuantity = initialQuantity
//    }

    private fun setupInputLayoutTextWatcher() {
        inputLayout.addTextChangedListener {
            selectedQuantity = it?.toString()?.toIntOrNull() ?: 0
        }
    }

    private fun setupNumpadButtons() {
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
        val saveButton = binding.saveQuantityButton
        saveButton.setOnClickListener {
            setFragmentResult(QUANTITY_REQUEST_KEY, bundleOf(QUANTITY_VALUE_KEY to selectedQuantity))
            findNavController().navigateUp()
        }
    }
}