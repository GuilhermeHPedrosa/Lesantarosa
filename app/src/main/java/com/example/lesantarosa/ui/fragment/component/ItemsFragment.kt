package com.example.lesantarosa.ui.fragment.component

import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.ui.adapter.recyclerview.ItemAdapter
import com.example.lesantarosa.ui.fragment.page.PageInventoryFragmentDirections
import com.example.lesantarosa.ui.viewmodel.InventoryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemsFragment: ListFragment<Item>() {

    private val viewModel: InventoryViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun initializeFilterFragment() {
        val filterFragment = SearchFragment()
            .also { setupSearchEndIcon(it) ; observeSearchUpdates(it) }

        this.filterFragment = filterFragment
    }

    override fun initializeRecyclerView() {
        val adapter = ItemAdapter(requireContext())
        adapter.setItemClick = { navigateToManagement(it) }

        this.adapter = adapter
    }

    private fun setupSearchEndIcon(searchFragment: SearchFragment) {
        searchFragment.icon = R.drawable.ic_add
        searchFragment.onEndIconClick = { navigateToManagement() }
    }

    private fun observeSearchUpdates(filterFragment: SearchFragment) {
        filterFragment.actualSearch.observe(viewLifecycleOwner) { search ->
             updateSource(viewModel.searchItems(search))
        }
    }

    private fun navigateToManagement(itemId: Long = -1L) {
        val direction = PageInventoryFragmentDirections.actionPageInventoryToPageManagement(itemId, itemType, 0)
        findNavController().navigate(direction)
    }
}