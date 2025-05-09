package com.example.lesantarosa.ui.fragment.component

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

class StockMovementFragment: Fragment() {

    private lateinit var binding: FragmentStockBinding

    private val viewModel: ManagementViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var actualStockInput: CustomInput

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
//        viewModel.getItemStock {
//            actualStockInput = CustomInput("Current Stock ", "Invalid Stock", Regex("^\\d+$"), InputType.TYPE_CLASS_NUMBER, it?.currentStock?.toString())
//
//            val inputLayout = actualStockInput.createInputLayout(requireContext())
//            binding.actualStockInput.addView(inputLayout)
//        }
    }

    private fun handleSaveButton() {
        val saveButton = binding.saveStockButton
        saveButton.setOnClickListener {
            try {
                viewModel
                findNavController().navigateUp()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}