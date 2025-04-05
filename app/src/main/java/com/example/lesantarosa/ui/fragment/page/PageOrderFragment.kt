package com.example.lesantarosa.ui.fragment.page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lesantarosa.R
import com.example.lesantarosa.databinding.FragmentDefaultPagerBinding
import com.example.lesantarosa.databinding.FragmentPageOrderBinding
import com.example.lesantarosa.models.data.VisualComponents
import com.example.lesantarosa.ui.fragment.component.CartFragment
import com.example.lesantarosa.ui.fragment.component.OrdersFragment
import com.example.lesantarosa.ui.viewmodel.OrderViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PageOrderFragment: PageFragment() {

    private lateinit var binding: FragmentPageOrderBinding

    private lateinit var viewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.defineVisualComponents(VisualComponents())

        viewModel = getViewModel<OrderViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeCartFragment()

        observeOrdersSummary()
    }

    private fun initializeCartFragment() {
        val ordersFragment = OrdersFragment()

        childFragmentManager
            .beginTransaction()
            .replace(R.id.orders_container, ordersFragment)
            .commit()
    }

    private fun observeOrdersSummary() {
        viewModel.getOrdersSummary().observe(viewLifecycleOwner) {
            binding.ordersSummaryText.text = it.toString()
        }
    }
}