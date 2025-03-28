package com.example.lesantarosa.ui.fragment.component

import androidx.fragment.app.viewModels
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.ui.adapter.recyclerview.SaleAdapter
import com.example.lesantarosa.ui.viewmodel.SaleViewModel

class ProductsForSaleFragment: ListFragment<ProductItem>() {

    private val viewModel by viewModels<SaleViewModel>(ownerProducer = { requireParentFragment() })

    override fun initializeFilterFragment() {
        val filterFragment = FilterFragment()
            .also { observeSearchUpdates(it) }

        this.filterFragment = filterFragment
    }

    private fun observeSearchUpdates(filterFragment: FilterFragment) {
        filterFragment.actualSearch.observe(viewLifecycleOwner) { search ->
            updateSource(viewModel.searchProductsForSale(search))
        }
    }

    override fun initializeRecyclerView() {
        val adapter = SaleAdapter(requireContext())
        adapter.setItemClick = { product ->
            viewModel.addToCart(product)
        }

        this.adapter = adapter
    }
}