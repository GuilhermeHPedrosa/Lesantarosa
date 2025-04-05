package com.example.lesantarosa.ui.fragment.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lesantarosa.R
import com.example.lesantarosa.databinding.InputSearchBinding
import com.google.android.material.textfield.TextInputLayout

class SearchFragment: Fragment() {

    private lateinit var binding: InputSearchBinding

    private val inputLayout by lazy { binding.searchInputLayout }
    private val inputEditText by lazy { binding.searchEditText }

    private val _actualSearch = MutableLiveData("")
    val actualSearch: LiveData<String?> get() = _actualSearch

    var onClearListener: () -> Unit = {}

    var icon: Int? = null
    var onEndIconClick: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InputSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configTextChangedListener()
        configOnFocusChangedListener()

        configEndIconButton()
    }

    private fun configTextChangedListener() {
        val handler = Handler(Looper.getMainLooper())
        var runnable: Runnable? = null

        inputEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputLayout.error = null
                inputLayout.isErrorEnabled = false
                runnable?.let { handler.removeCallbacks(it) }
            }

            override fun afterTextChanged(p0: Editable?) {
                runnable = Runnable { _actualSearch.value = p0?.toString() ?: "" }
                handler.postDelayed(runnable!!, 400)
            }
        })
    }

    private fun configOnFocusChangedListener() {
        inputEditText.setOnFocusChangeListener { _, focused ->
            if (focused) {
                inputLayout.setStartIconOnClickListener { clear() }
                inputLayout.startIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)
            } else {
                inputLayout.setStartIconOnClickListener {}
                inputLayout.startIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_bloom)
            }
        }
    }

    private fun configEndIconButton() {
        val icon = icon ?: return

        inputLayout.setEndIconDrawable(icon)
        inputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM

        inputLayout.setEndIconOnClickListener { onEndIconClick() }
    }

    private fun clear() {
        inputEditText.text = null
        inputEditText.clearFocus()

        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(inputEditText.windowToken, 0)

        onClearListener()
    }
}