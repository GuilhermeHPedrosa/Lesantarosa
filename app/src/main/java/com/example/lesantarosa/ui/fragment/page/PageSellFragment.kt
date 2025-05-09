package com.example.lesantarosa.ui.fragment.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.FragmentKeys.QUANTITY_REQUEST_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.QUANTITY_VALUE_KEY
import com.example.lesantarosa.databinding.FragmentPageSellBinding
import com.example.lesantarosa.ui.fragment.component.ProductsForSaleFragment
import com.example.lesantarosa.ui.viewmodel.SaleViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PageSellFragment: PageFragment() {

    private lateinit var binding: FragmentPageSellBinding

    private lateinit var viewModel: SaleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel<SaleViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageSellBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeSaleFragment()

        observeSaleSummary()
        observeSaleQuantitySelectionResult()

        handleGoToCartButton()
    }

    private fun initializeSaleFragment() {
        val saleFragment = ProductsForSaleFragment()

        childFragmentManager
            .beginTransaction()
            .replace(R.id.products_for_sale_container, saleFragment)
            .commit()
    }

    private fun observeSaleSummary() {
        viewModel.saleSummary.observe(viewLifecycleOwner) { summary ->
            with(binding.goToCartButton) {
                text = summary.getSummary(requireContext())
                isEnabled = summary.itemCount > 0
            }
        }
    }

    private fun observeSaleQuantitySelectionResult() {
        setFragmentResultListener(QUANTITY_REQUEST_KEY) { _, bundle ->
            val selectedQuantity = bundle.getInt(QUANTITY_VALUE_KEY)
            selectedQuantity.takeIf { it > 0 }?.let { viewModel.updateSaleQuantity(it) }
        }
    }

    private fun handleGoToCartButton() {
        val goToCartButton = binding.goToCartButton
        goToCartButton.setOnClickListener {
            val direction = PageSellFragmentDirections.actionPageSellToPageCart()
            findNavController().navigate(direction)
        }
    }
}