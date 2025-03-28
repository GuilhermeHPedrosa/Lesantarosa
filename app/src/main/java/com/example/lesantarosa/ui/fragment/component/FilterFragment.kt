package com.example.lesantarosa.ui.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.databinding.CardFilterBinding
import com.example.lesantarosa.ui.fragment.page.PageSellFragmentDirections

class FilterFragment: Fragment() {

    private lateinit var binding: CardFilterBinding

    private val searchFragmentContainer by lazy { binding.filterSearchFragmentContainer }
    private val filterButtonLinearLayout by lazy { binding.filterButtonLinearLayout }

    private val searchFragment = SearchFragment()

    val actualSearch: LiveData<String?> get() = searchFragment.actualSearch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CardFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchFragment()
        setupSearchFragmentIcon()

        handleSearchButton()

        handleToQuantityDialogButton()
        handleToPriceDialogButton()
    }

    private fun setupSearchFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.filterSearchFragmentContainer, searchFragment)
            .commit()
    }

    private fun setupSearchFragmentIcon() {
        val drawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_close)
        searchFragment.setupIconValues(drawable) { handleClearSearch() }
    }

    private fun handleClearSearch() {
        searchFragmentContainer.apply {
            visibility = View.GONE

            searchFragment.clear()
            clearFocus()

            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(this.windowToken, 0)
        }

        filterButtonLinearLayout.visibility = View.VISIBLE
    }

    private fun handleSearchButton() {
        val searchButton = binding.filterSearchButton
        searchButton.setOnClickListener {
            searchFragmentContainer.apply {
                visibility = View.VISIBLE
                requestFocus()
            }

            filterButtonLinearLayout.visibility = View.GONE
        }
    }

    private fun handleToQuantityDialogButton() {
        val selectQuantityButton = binding.filterSelectQuantityButton
        selectQuantityButton.setOnClickListener {
            val direction = PageSellFragmentDirections.actionPageSellToQuantityDialog(1)
            findNavController().navigate(direction)
        }
    }

    private fun handleToPriceDialogButton() {
        val unregisteredItemButton = binding.filterUnregisteredItemButton
        unregisteredItemButton.setOnClickListener {
            val direction = PageSellFragmentDirections.actionPageSellToPriceDialog(0f, 100000f)
            findNavController().navigate(direction)
        }
    }
}