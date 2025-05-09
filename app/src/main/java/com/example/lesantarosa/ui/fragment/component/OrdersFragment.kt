package com.example.lesantarosa.ui.fragment.component

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lesantarosa.models.entities.Order
import com.example.lesantarosa.models.enums.OrderStatus
import com.example.lesantarosa.models.sealed.OrderItem
import com.example.lesantarosa.models.sealed.OrderItem.DateHeader
import com.example.lesantarosa.models.sealed.OrderItem.OrderEntry
import com.example.lesantarosa.ui.adapter.OrderAdapter
import com.example.lesantarosa.ui.viewmodel.OrderViewModel
import java.time.Instant
import java.time.ZoneId

class OrdersFragment: ListFragment<OrderItem>() {

    private val viewModel: OrderViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var filterMediator: LiveData<Pair<String?, OrderStatus?>>

    override fun initializeFilterFragment() {
        val orderFilterFragment = OrderFilterFragment()
        filterMediator = orderFilterFragment.filterMediator

        this.filterFragment = orderFilterFragment
    }

    override fun initializeRecyclerView() {
        val adapter = OrderAdapter(requireContext())
        adapter.setItemClick = {}

        this.adapter = adapter
    }

    override fun observeListFilters() {
        filterMediator.observe(viewLifecycleOwner) { it ->
            val newSource = viewModel.searchOrders(it.first, it.second).map { formatOrderItems(it) }
            updateSource(newSource)
        }
    }

    private fun formatOrderItems(orders: List<Order>): List<OrderItem> {
        return orders
            .groupBy { Instant.ofEpochMilli(it.createdAt).atZone(ZoneId.systemDefault()).toLocalDate() }
            .flatMap { (date, orders) -> listOf(DateHeader(date, orders.sumOf { it.finalAmount }, orders.size)) + orders.map { OrderEntry(it) } }
    }
}