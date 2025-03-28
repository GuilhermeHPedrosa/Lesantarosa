package com.example.lesantarosa.ui.fragment.component

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lesantarosa.databinding.InputSearchBinding

class SearchFragment: Fragment() {

    private lateinit var binding: InputSearchBinding

    private val _actualSearch = MutableLiveData("")
    val actualSearch: LiveData<String?> get() = _actualSearch

    private var icon: Drawable? = null
    private var position: Int? = null
    private var onIconClick: () -> Unit = {}

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

        configInputLayout()
        configIconButton()
    }

    private fun configInputLayout() {
        val inputLayout = binding.searchInputLayout
        val inputEditText = binding.searchEditText

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

    private fun configIconButton() {
        val inputLayout = binding.searchInputLayout

        when (position) {
            0 -> {
                inputLayout.startIconDrawable = icon
                inputLayout.setStartIconOnClickListener { onIconClick() }
            }
            1 -> {
                inputLayout.endIconDrawable = icon
                inputLayout.setEndIconOnClickListener { onIconClick() }
            }
        }
    }

    fun setupIconValues(icon: Drawable?, position: Int = 0, onIconClick: () -> Unit) {
        this.icon = icon
        this.position = position
        this.onIconClick = onIconClick
    }

    fun clear() {
        binding.searchEditText.text = null
    }
}