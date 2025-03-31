package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.databinding.FragmentCreationBinding
import com.example.lesantarosa.models.data.RecipeItem
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.models.sealed.ItemInputProvider
import com.example.lesantarosa.ui.viewmodel.ManagementViewModel
import java.time.Instant
import java.time.temporal.ChronoUnit

class CreationFragment: Fragment() {

    private lateinit var binding: FragmentCreationBinding

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
            initializeItemInputLayouts()
        }

        handleSaveButton()
    }

    private fun initializeInputProvider(item: Item?) {
        inputProvider = when (itemType) {
            ItemType.PRODUCT -> ItemInputProvider.Product(item)
            ItemType.RECIPE -> ItemInputProvider.Recipe(item)
            ItemType.INGREDIENT -> ItemInputProvider.Ingredient(item)
        }
    }

    private fun initializeItemInputLayouts() {
        val inputFieldsLinearLayout = binding.inputFieldsLinearLayout

        val itemInputs = inputProvider.getItemInputs
        itemInputs.forEach { inputFieldsLinearLayout.addView(it.createInputLayout(requireContext())) }
    }

    private fun handleSaveButton() {
        val confirmButton = binding.confirmButton
        confirmButton.setOnClickListener {
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