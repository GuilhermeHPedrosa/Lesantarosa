package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.databinding.FragmentCreationBinding
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.enums.ItemType
import com.example.lesantarosa.ui.viewmodel.ManagementViewModel
import java.time.Instant
import java.time.temporal.ChronoUnit

abstract class CreationFragment: Fragment() {

    protected lateinit var binding: FragmentCreationBinding

    private lateinit var itemInputFields: List<CustomInput>

    protected val viewModel: ManagementViewModel by viewModels(ownerProducer = { requireParentFragment() })

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

        handleSaveButton()
    }

    protected open fun initializeItemFields(item: Item?) {
        itemInputFields = listOf(
            CustomInput("Title", "Invalid Title", Regex("^[a-zA-Z0-9\\s!@#\$%^&*()_+\\-=\\[\\]{};':\",./?]{4,}$"), InputType.TYPE_CLASS_TEXT, item?.title),
            CustomInput("Description", "Invalid Description", Regex("^[a-zA-Z]{4,}\$"), InputType.TYPE_CLASS_TEXT, item?.description),
            CustomInput("Price", "Invalid Price", Regex("^\\d+([.,]\\d{1,2})?\$"), (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL), item?.price?.toString()),
            CustomInput("Shelf Life ", "Invalid Shelf Life", Regex("^\\d+$"), InputType.TYPE_CLASS_NUMBER, item?.price?.toString())
        )
    }

    protected open fun initializeItemsInputLayout() {
        val inputFieldsLinearLayout = binding.creationLayout.inputFieldsLinearLayout
        itemInputFields.forEach { inputFieldsLinearLayout.addView(it.createInputLayout(requireContext())) }
    }

    protected fun createRawItem(): Item {
        val title = itemInputFields[0].getInputValue()
        val description = itemInputFields[1].getInputValue()
        val price = itemInputFields[2].getInputValue().toDouble()

        val actualDate = Instant.now()
        val expirationDate = actualDate.minus(itemInputFields[3].getInputValue().toLong(), ChronoUnit.DAYS)

        return Item(viewModel.itemId, title, description, null, price, expirationDate.toEpochMilli(), actualDate.toEpochMilli(), null)
    }

    private fun handleSaveButton() {
        val saveButton = binding.saveButton
        saveButton.setOnClickListener {
            try {
                viewModel.saveItem(createItem()) {
                    Toast.makeText(requireContext(), "Item Salvo", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    abstract fun createItem(): Item

    companion object {
        fun newInstance(): CreationFragment {
            return when(itemType) {
                ItemType.PRODUCT -> CreationProductFragment()
                ItemType.RECIPE -> CreationRecipeFragment()
                ItemType.INGREDIENT -> CreationIngredientFragment()
            }
        }
    }
}