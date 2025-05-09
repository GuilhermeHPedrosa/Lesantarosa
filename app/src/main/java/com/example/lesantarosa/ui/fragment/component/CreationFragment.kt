package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.databinding.FragmentCreationBinding
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.models.sealed.ItemInputProvider
import com.example.lesantarosa.ui.viewmodel.ManagementViewModel

class CreationFragment: Fragment() {

    private lateinit var binding: FragmentCreationBinding

    private val itemLinearLayout by lazy { binding.cardItemCreation.itemInputLinearLayout }
    private val childLinearLayout by lazy { binding.cardChildCreation.childInputLinearLayout }

    private val viewModel: ManagementViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var inputProvider: ItemInputProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getItem {
            initializeInputProvider(it)

            initializeItemInputs()
            initializeChildInputs()
        }

        handleChildCardClick()
        handleSaveButton()
    }

    private fun initializeInputProvider(item: Item?) {
        inputProvider = when (itemType) {
            ItemType.PRODUCT -> ItemInputProvider.Product(item)
            ItemType.RECIPE -> ItemInputProvider.Recipe(item)
            ItemType.INGREDIENT -> ItemInputProvider.Ingredient(item)
        }
    }

    private fun initializeItemInputs() {
        inputProvider.getItemInputs.forEach {
            val inputLayout = it.createInputLayout(requireContext())
            itemLinearLayout.addView(inputLayout)
        }
    }

    private fun initializeChildInputs() {
        inputProvider.getChildInputs.forEach {
            val inputLayout = it.createInputLayout(requireContext())
            childLinearLayout.addView(inputLayout)
        }
    }

    private fun handleChildCardClick() {
        val cardTextview = binding.cardChildCreation.childCreationTextview
        cardTextview.setOnClickListener {
            it.isSelected = !it.isSelected
            childLinearLayout.isVisible = !childLinearLayout.isVisible
        }
    }

    private fun handleSaveButton() {
        val saveItemButton = binding.saveItemButton
        saveItemButton.setOnClickListener {
            try {
                val item = inputProvider.createItem()
                viewModel.saveItem(item) {
                    Toast.makeText(requireContext(), "Item Salvo", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}