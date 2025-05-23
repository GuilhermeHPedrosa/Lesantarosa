package com.example.lesantarosa.ui.fragment.component

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.models.data.StockItem
import com.example.lesantarosa.ui.adapter.StockAdapter
import com.example.lesantarosa.ui.fragment.page.PageInventoryFragmentDirections
import com.example.lesantarosa.ui.viewmodel.InventoryViewModel

class StockCatalogFragment: ListFragment<StockItem>() {

    private val viewModel: InventoryViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var actualSearch: LiveData<String?>

    override fun initializeFilterFragment() {
        this.filterFragment = SearchFragment(R.string.input_search_stock_hint, R.drawable.ic_add, ::navigateToManagement)
            .also { actualSearch = it.actualSearch }
    }

    override fun initializeRecyclerView() {
        val adapter = StockAdapter(requireContext())
        adapter.setItemClick = { navigateToManagement(it.itemId, it.title) }

        this.adapter = adapter
    }

    override fun observeListFilters() {
        actualSearch.observe(viewLifecycleOwner) { search ->
            val newSource = viewModel.searchStocks(search)
            updateSource(newSource)
        }
    }

    private fun navigateToManagement(itemId: Long = -1L, title: String? = null) {
        val direction = PageInventoryFragmentDirections.actionPageInventoryToPageManagement(itemId, title, itemType, 1)
        findNavController().navigate(direction)
    }
}