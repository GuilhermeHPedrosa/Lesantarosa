package com.example.lesantarosa.ui.fragment.component

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.provider.Settings.Global.getString
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.annotation.StringRes
import com.example.lesantarosa.databinding.InputCustomBinding
import com.google.android.material.textfield.TextInputLayout

class CustomInput(
    private val context: Context,
    @StringRes private val hint: Int,
    @StringRes private val error: Int,
    private val regex: Regex,
    private val inputType: Int,
    initialValue: String? = null
) {

    private var inputValue = initialValue ?: ""
    private val handler = Handler(Looper.getMainLooper())

    fun createInputLayout(): TextInputLayout {
        val binding = InputCustomBinding.inflate(LayoutInflater.from(context))

        val inputLayout = binding.customInputLayout
        val inputEditText = binding.customEditText

        inputLayout.hint = context.getString(hint)

        inputEditText.inputType = inputType
        inputEditText.setText(inputValue)

        var runnable: Runnable? = null

        inputEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputLayout.error = null
                inputLayout.isErrorEnabled = false
                runnable?.let { handler.removeCallbacks(it) }
            }

            override fun afterTextChanged(p0: Editable?) {
                val input = p0?.toString() ?: ""

                runnable = Runnable {
                    inputValue = input
                    validateInput(inputLayout, input)
                }
                handler.postDelayed(runnable!!, 400)
            }
        })
        return binding.customInputLayout
    }

    private fun validateInput(inputLayout: TextInputLayout, text: String) {
        if (text.isNotBlank() && !regex.matches(text)) {
            inputLayout.error = context.getString(hint)
            inputLayout.isErrorEnabled = true
        } else {
            inputLayout.error = null
            inputLayout.isErrorEnabled = false
        }
    }

    fun getInputValue(): String {
        return inputValue.takeIf { regex.matches(it) }
            ?: throw IllegalArgumentException()
    }
}