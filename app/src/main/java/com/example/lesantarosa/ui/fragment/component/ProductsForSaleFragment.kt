package com.example.lesantarosa.ui.fragment.component

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.ui.adapter.SaleAdapter
import com.example.lesantarosa.ui.viewmodel.SaleViewModel

class ProductsForSaleFragment: ListFragment<ProductItem>() {

    private val viewModel: SaleViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var actualSearch: LiveData<String?>

    override fun initializeFilterFragment() {
        val saleFilterFragment = SaleFilterFragment()
        actualSearch = saleFilterFragment.actualSearch

        this.filterFragment = saleFilterFragment
    }

    override fun initializeRecyclerView() {
        val adapter = SaleAdapter(requireContext())
        adapter.setItemClick = { viewModel.addToCart(it) }

        this.adapter = adapter
    }

    override fun observeListFilters() {
        actualSearch.observe(viewLifecycleOwner) { search ->
            val newSource = viewModel.searchProductsForSale(search)
            updateSource(newSource)
        }
    }
}