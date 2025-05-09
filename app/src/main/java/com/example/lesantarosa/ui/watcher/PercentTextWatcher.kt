package com.example.lesantarosa.ui.watcher

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.Locale

class PercentTextWatcher(
    private val editText: EditText,
    private val totalCurrency: Double,
    private val onTextChanged: (selectedPrice: Double) -> Unit
): TextWatcher {

    private var isEditing = false

    override fun afterTextChanged(editable: Editable?) {
        if (isEditing || editable.isNullOrEmpty()) return

        val cleanString = editable.toString()
            .replace(Regex("\\D"), "")
            .padStart(4, '0')

        val parsedPercent = cleanString.toLong()
            .coerceIn(0, 10000) / 100.0

        val parsedPrice = (parsedPercent / 100.0) * totalCurrency

        updateCurrency(parsedPrice)
        onTextChanged(parsedPrice)
    }

    private fun formatToPercent(price: Double): Double {
        return (price / totalCurrency) * 100
    }

    private fun formatPercentText(price: Double): String {
        val formatedPercent = formatToPercent(price)
        return String.format(Locale("pt", "BR"), "%05.2f", formatedPercent)
    }

    fun updateCurrency(price: Double) {
        isEditing = true

        val formattedText = formatPercentText(price)
        editText.setText(formattedText)
        editText.setSelection(formattedText.length)

        isEditing = false
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}