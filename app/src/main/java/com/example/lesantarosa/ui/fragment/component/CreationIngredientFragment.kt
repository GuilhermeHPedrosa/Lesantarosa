package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.text.InputType
import android.view.View
import com.example.lesantarosa.models.data.IngredientItem
import com.example.lesantarosa.models.entities.Item

class CreationIngredientFragment: CreationFragment() {

    private lateinit var ingredientInputFields: List<CustomInput>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getItem {
            initializeItemFields(it)
            initializeItemsInputLayout()
        }
    }

    override fun initializeItemFields(item: Item?) {
        super.initializeItemFields(item)

        val ingredientItem = item as? IngredientItem
        ingredientInputFields = listOf(
            CustomInput("Brand", "Invalid Brand", Regex("^[A-Za-zÀ-ÿ\\s'-]+\$"), InputType.TYPE_CLASS_TEXT, ingredientItem?.brand),
            CustomInput("Supplier", "Invalid Supplier", Regex("^[A-Za-zÀ-ÿ\\s'-]+\$"), InputType.TYPE_CLASS_TEXT, ingredientItem?.supplier),
            CustomInput("Nutritional Info", "Invalid Nutritional", Regex("^\\d+([.,]\\d{1,2})?\$"), InputType.TYPE_CLASS_NUMBER, ingredientItem?.nutritionalInfo)
        )
    }

    override fun initializeItemsInputLayout() {
        super.initializeItemsInputLayout()

        val inputFieldsLinearLayout = binding.entityCreationLayout.inputFieldsLinearLayout
        ingredientInputFields.forEach { inputFieldsLinearLayout.addView(it.createInputLayout(requireContext())) }
    }

    override fun createItem(): Item {
        val item = createRawItem()
        val brand = ingredientInputFields[0].getInputValue()
        val supplier = ingredientInputFields[1].getInputValue()
        val nutritionalInfo = ingredientInputFields[2].getInputValue()

        return IngredientItem.createIngredient(item, brand, supplier, nutritionalInfo)
    }
}