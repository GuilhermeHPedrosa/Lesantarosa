package com.example.lesantarosa.ui.fragment.bottomsheet

import android.content.Context
import android.view.LayoutInflater
import com.example.lesantarosa.databinding.DialogCartOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CartOptionsBottomSheetDialog(context: Context): BottomSheetDialog(context) {

    private val binding: DialogCartOptionsBinding = DialogCartOptionsBinding.inflate(LayoutInflater.from(context))

    var noteButtonListener: () -> Unit = {}
    var discountButtonListener: () -> Unit = {}
    var clearCartButtonListener: () -> Unit = {}

    init {
        setContentView(binding.root)

        setupFunctionButtons()
        setupCloseButton()
    }

    private fun setupFunctionButtons() {
        binding.goToNoteButton.setOnClickListener { noteButtonListener() ; dismiss()}
        binding.goToDiscountButton.setOnClickListener { discountButtonListener() ; dismiss() }
        binding.clearCartButton.setOnClickListener { clearCartButtonListener() ; dismiss() }
    }

    private fun setupCloseButton() {
        binding.closeOptionsButton.setOnClickListener { dismiss() }
    }
}