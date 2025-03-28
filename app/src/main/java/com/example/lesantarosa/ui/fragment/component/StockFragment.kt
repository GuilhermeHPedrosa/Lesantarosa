package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.databinding.FragmentStockBinding
import com.example.lesantarosa.models.enums.MeasureUnit
import com.example.lesantarosa.models.entities.Stock
import com.example.lesantarosa.ui.viewmodel.ManagementViewModel
import kotlin.random.Random

class StockFragment: Fragment() {

    private lateinit var binding: FragmentStockBinding

    private val viewModel: ManagementViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStockInfo()

        handleSaveButton()
    }

    private fun setupStockInfo() {
        viewModel.getItemStock {
            it?.let { setupStockInputValues(it) }
        }
    }

    private fun setupStockInputValues(stock: Stock) {
        val minStockInput = binding.minStockInputLayout
        minStockInput.setText(stock.minStock.toString())

        val maxStockInput = binding.maxStockInputLayout
        maxStockInput.setText(stock.maxStock.toString())

        val actualStockInput = binding.actualStockInputLayout
        actualStockInput.setText(stock.currentStock.toString())
    }

    private fun createStock(): Stock {
        val minStockInput = binding.minStockInputLayout
        val maxStockInput = binding.maxStockInputLayout
        val actualStockInput = binding.actualStockInputLayout

        val minStock = minStockInput.text.takeIf { it.isNotBlank() }?.toString()?.toInt()
                ?: throw IllegalArgumentException("Invalid Min Stock")

        val maxStock = maxStockInput.text.takeIf { it.isNotBlank() }?.toString()?.toInt()
            ?: throw IllegalArgumentException("Invalid Max Stock")

        val currentStock = actualStockInput.text.takeIf { it.isNotBlank() }?.toString()?.toInt()
                ?: throw Exception("Invalid Actual Stock")

        return Stock(Random.nextLong(), viewModel.itemId, minStock, maxStock, currentStock, 0,MeasureUnit.GRAM, itemType)
    }

    private fun handleSaveButton() {
        val saveButton = binding.saveStockButton
        saveButton.setOnClickListener {
            try {
                viewModel.saveStock(createStock()) {
                    Toast.makeText(requireContext(), "Stock Salvo", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}