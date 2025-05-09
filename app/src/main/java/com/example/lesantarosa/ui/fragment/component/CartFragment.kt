package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lesantarosa.databinding.FragmentCartBinding
import com.example.lesantarosa.models.data.CartSummary
import com.example.lesantarosa.ui.adapter.CartAdapter
import com.example.lesantarosa.ui.viewmodel.CartViewModel

class CartFragment: Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()

        setupRecyclerView()

        observeCartProducts()
        observeCartSummary()
    }

    private fun initializeRecyclerView() {
        val adapter = CartAdapter(requireContext())
        adapter.setItemClick = {}

        this.adapter = adapter
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.cartRecyclerview
        recyclerView.adapter = adapter
    }

    private fun setupSummaryInfo(summary: CartSummary) {
        val cartSummary = binding.cartSummary

        val totalAmount = cartSummary.cartTotalAmountValue
        totalAmount.text = summary.formattedTotalAmount

        val totalDiscounts = cartSummary.cartTotalDiscountsValue
        totalDiscounts.text = summary.formattedDiscounts

        val finalPrice = cartSummary.cartFinalPriceValue
        finalPrice.text = summary.formattedFinalPrice
    }

    private fun observeCartProducts() {
        viewModel.getCartProducts().observe(viewLifecycleOwner) {
            adapter.refresh(it ?: emptyList())
        }
    }

    private fun observeCartSummary() {
        viewModel.cartSummary.observe(viewLifecycleOwner) {
            setupSummaryInfo(it)
        }
    }
}