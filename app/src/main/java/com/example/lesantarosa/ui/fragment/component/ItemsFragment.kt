package com.example.lesantarosa.ui.fragment.component

import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.ui.adapter.recyclerview.ItemAdapter
import com.example.lesantarosa.ui.fragment.page.PageInventoryFragmentDirections
import com.example.lesantarosa.ui.viewmodel.InventoryViewModel

class ItemsFragment: ListFragment<Item>() {

    private val viewModel: InventoryViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun initializeFilterFragment() {
        val filterFragment = SearchFragment()
            .also { setupSearchIcon(it) ; observeSearchUpdates(it) }

        this.filterFragment = filterFragment
    }

    override fun initializeRecyclerView() {
        val adapter = ItemAdapter(requireContext())
        adapter.setItemClick = { navigateToManagement(it.itemId) }

        this.adapter = adapter
    }

    private fun setupSearchIcon(filterFragment: SearchFragment) {
        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_add)?.let {
            filterFragment.setupIconValues(it, 1) {
                navigateToManagement(-1L)
            }
        }
    }

    private fun observeSearchUpdates(filterFragment: SearchFragment) {
        filterFragment.actualSearch.observe(viewLifecycleOwner) { search ->
             updateSource(viewModel.searchItems(search))
        }
    }

    private fun navigateToManagement(itemId: Long) {
        val direction = PageInventoryFragmentDirections.actionPageInventoryToPageManagement(itemId, itemType, 0)
        findNavController().navigate(direction)
    }
}