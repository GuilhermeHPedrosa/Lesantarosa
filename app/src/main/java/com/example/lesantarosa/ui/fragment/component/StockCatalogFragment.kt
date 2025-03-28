package com.example.lesantarosa.ui.fragment.component

import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.models.entities.Stock
import com.example.lesantarosa.ui.adapter.recyclerview.StockAdapter
import com.example.lesantarosa.ui.fragment.page.PageInventoryFragmentDirections
import com.example.lesantarosa.ui.viewmodel.InventoryViewModel

class StockCatalogFragment: ListFragment<Stock>() {

    private val viewModel: InventoryViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun initializeFilterFragment() {
        val searchFragment = SearchFragment()
            .also { setupSearchIcon(it) ; observeSearchUpdates(it) }

        this.filterFragment = searchFragment
    }

    override fun initializeRecyclerView() {
        val adapter = StockAdapter(requireContext())
        adapter.setItemClick = { navigateToManagement(it) }

        this.adapter = adapter
    }

    private fun setupSearchIcon(filterFragment: SearchFragment) {
        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_add)?.let { icon ->
            filterFragment.setupIconValues(icon, 1) {
                navigateToManagement(-1L)
            }
        }
    }

    private fun observeSearchUpdates(filterFragment: SearchFragment) {
        filterFragment.actualSearch.observe(viewLifecycleOwner) { search ->
            updateSource(viewModel.searchStocks(search))
        }
    }

    private fun navigateToManagement(itemId: Long) {
        val direction = PageInventoryFragmentDirections.actionPageInventoryToPageManagement(itemId, itemType, 1)
        findNavController().navigate(direction)
    }
}