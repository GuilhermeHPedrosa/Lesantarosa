package com.example.lesantarosa.ui.fragment.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lesantarosa.R
import com.example.lesantarosa.databinding.InputSearchBinding
import com.google.android.material.textfield.TextInputLayout
import java.lang.Thread.State

class SearchFragment(
    @StringRes private val hint: Int,
    @DrawableRes private val icon: Int? = null,
    val onIconClick: () -> Unit = {}
): Fragment() {

    private lateinit var binding: InputSearchBinding

    private val inputLayout by lazy { binding.searchInputLayout }
    private val inputEditText by lazy { binding.searchEditText }

    private val _actualSearch = MutableLiveData("")
    val actualSearch: LiveData<String?> get() = _actualSearch

    var onClearListener: () -> Unit = {}

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

        setupTextChangedListener()
        setupEndIconButton()

        setupOnFocusChangedListener()
        setupInputLayoutUnfocused()
    }

    private fun setupTextChangedListener() {
        val handler = Handler(Looper.getMainLooper())

        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val runnable = Runnable { _actualSearch.value = p0?.toString() ?: "" }
                handler.postDelayed(runnable, 400)
            }
        })
    }

    private fun setupOnFocusChangedListener() {
        inputEditText.setOnFocusChangeListener { _, focused ->
            if (focused) setupInputLayoutFocused()
            else setupInputLayoutUnfocused()
        }
    }

    private fun setupInputLayoutFocused() {
        inputLayout.setStartIconDrawable(R.drawable.ic_close)
        inputLayout.setStartIconOnClickListener { clear() }

        inputLayout.isEndIconVisible = false

        inputEditText.hint = getString(R.string.input_search_on_focus_hint)
    }

    private fun setupInputLayoutUnfocused() {
        inputLayout.setStartIconDrawable(R.drawable.ic_bloom)
        inputLayout.setStartIconOnClickListener(null)

        inputLayout.isStartIconVisible = false
        inputLayout.isStartIconVisible = true
        inputLayout.isEndIconVisible = true

        inputEditText.text = null
        inputEditText.hint = getString(hint)
    }

    private fun setupEndIconButton() {
        val icon = icon ?: return

        inputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM

        inputLayout.setEndIconDrawable(icon)
        inputLayout.setEndIconOnClickListener { onIconClick() }
    }

    private fun clear() {
        inputEditText.text = null
        inputEditText.clearFocus()

        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(inputEditText.windowToken, 0)

        onClearListener()
    }
}