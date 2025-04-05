package com.example.lesantarosa.ui.fragment.component

import androidx.fragment.app.viewModels
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.ui.adapter.recyclerview.SaleAdapter
import com.example.lesantarosa.ui.viewmodel.SaleViewModel

class ProductsForSaleFragment: ListFragment<ProductItem>() {

    private val viewModel by viewModels<SaleViewModel>(ownerProducer = { requireParentFragment() })

    override fun initializeFilterFragment() {
        val saleFilterFragment = SaleFilterFragment()
            .also { observeSearchUpdates(it) }

        this.filterFragment = saleFilterFragment
    }

    override fun initializeRecyclerView() {
        val adapter = SaleAdapter(requireContext())
        adapter.setItemClick = { product ->
            viewModel.addToCart(product)
        }

        this.adapter = adapter
    }

    private fun observeSearchUpdates(saleFilterFragment: SaleFilterFragment) {
        saleFilterFragment.actualSearch.observe(viewLifecycleOwner) { search ->
            updateSource(viewModel.searchProductsForSale(search))
        }
    }
}