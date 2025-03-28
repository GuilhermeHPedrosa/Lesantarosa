package com.example.lesantarosa.ui.adapter.textwatcher

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale

class PriceTextWatcher(
    private val editText: EditText,
    private val priceLimit: Double,
    initialPrice: Double = 0.0,
    private val onPriceChanged: (price: Double) -> Unit
): TextWatcher {

    private var isEditing = false
    private val priceFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    init { updatePrice(initialPrice) }

    override fun afterTextChanged(editable: Editable?) {
        if (isEditing || editable.isNullOrEmpty()) return

        val cleanString = editable.toString()
            .replace(Regex("\\D"), "")
            .padStart(4, '0')

        val parsedPrice = (cleanString.toLong() / 100.0)
            .takeIf { it <= priceLimit } ?: priceLimit

        updatePrice(parsedPrice)
        onPriceChanged(parsedPrice)
    }

    fun updatePrice(price: Double) {
        isEditing = true

        val formattedText = priceFormat.format(price)
        editText.setText(formattedText)
        editText.setSelection(formattedText.length)

        isEditing = false
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}