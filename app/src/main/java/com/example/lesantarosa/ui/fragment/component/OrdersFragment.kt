package com.example.lesantarosa.ui.fragment.component

import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.models.sealed.OrderItem
import com.example.lesantarosa.models.sealed.OrderItem.DateHeader
import com.example.lesantarosa.models.sealed.OrderItem.OrderEntry
import com.example.lesantarosa.ui.adapter.recyclerview.OrderAdapter
import com.example.lesantarosa.ui.viewmodel.OrderViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class OrdersFragment: ListFragment<OrderItem>() {

    private val viewModel: OrderViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun initializeFilterFragment() {
        val orderFilterFragment = OrderFilterFragment()
            .also { observeFilterUpdates(it) }

        this.filterFragment = orderFilterFragment
    }

    override fun initializeRecyclerView() {
        val adapter = OrderAdapter(requireContext())
        adapter.setItemClick = {}

        this.adapter = adapter
    }

    private fun observeFilterUpdates(orderFilterFragment: OrderFilterFragment) {
        orderFilterFragment.filterMediator.observe(viewLifecycleOwner) { it ->
            val orders = viewModel.searchOrders(it.first, it.second)
                .map { formatOrderItems(it) }

            updateSource(orders)
        }
    }

    private fun formatOrderItems(orders: List<Order>): List<OrderItem> {
        return orders
            .groupBy { Instant.ofEpochMilli(it.createdAt).atZone(ZoneId.systemDefault()).toLocalDate() }
            .flatMap { (date, orders) -> listOf(DateHeader(date, orders.sumOf { it.finalAmount }, orders.size)) + orders.map { OrderEntry(it) } }
    }
}