package com.example.lesantarosa.ui.fragment.component

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.databinding.FragmentStockBinding
import com.example.lesantarosa.models.entities.StockMovement
import com.example.lesantarosa.ui.adapter.StockMovementAdapter
import com.example.lesantarosa.ui.viewmodel.ManagementViewModel
import kotlin.random.Random

class StockFragment: ListFragment<StockMovement>() {

    private val viewModel: ManagementViewModel by viewModels(ownerProducer = { requireParentFragment().requireParentFragment() })

    override fun initializeFilterFragment() {
        filterFragment = Fragment()
    }

    override fun initializeRecyclerView() {
        val adapter = StockMovementAdapter(requireContext())
        adapter.setDeleteClick = { movementId ->
            viewModel.removeMovement(movementId, {})
        }

        this.adapter = adapter
    }

    override fun observeListFilters() {
        //val newSource = viewModel.getStockMovements()
        val newSource = MutableLiveData(listOf(
            StockMovement(Random.nextLong(), Random.nextLong(), 1, 1), StockMovement(
                Random.nextLong(), Random.nextLong(), 1, 1)
        ))
        updateSource(newSource)
    }
}