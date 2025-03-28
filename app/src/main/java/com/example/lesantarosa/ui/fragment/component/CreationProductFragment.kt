package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.text.InputType
import android.view.View
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.data.ProductItem

class CreationProductFragment: CreationFragment() {

    private lateinit var productInputFields: List<CustomInput>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getItem {
            initializeItemFields(it)
            initializeItemsInputLayout()
        }
    }

    override fun initializeItemFields(item: Item?) {
        super.initializeItemFields(item)

        val productItem = item as? ProductItem
        productInputFields = listOf(
            CustomInput("Weight", "Invalid Weight", Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, productItem?.discountPrice?.toString()),
            CustomInput("Packaging", "Invalid Packaging", Regex("^[A-Za-z]+\$"), InputType.TYPE_CLASS_TEXT, productItem?.salesCount?.toString()),
            CustomInput("Discount Price", "Invalid Price", Regex("^\\d+([.,]\\d{1,2})?\$"), InputType.TYPE_CLASS_NUMBER, productItem?.discountPrice?.toString()),
            CustomInput("Sales Count", "Invalid Quantity", Regex("^\\d+([.,]\\d{1,2})?\$"), InputType.TYPE_CLASS_NUMBER, productItem?.salesCount?.toString())
        )
    }

    override fun initializeItemsInputLayout() {
        super.initializeItemsInputLayout()

        val inputFieldsLinearLayout = binding.entityCreationLayout.inputFieldsLinearLayout
        productInputFields.forEach { inputFieldsLinearLayout.addView(it.createInputLayout(requireContext())) }
    }

    override fun createItem(): Item {
        val item = createRawItem()
        val weight = productInputFields[0].getInputValue().toInt()
        val packaging = productInputFields[1].getInputValue()
        val discountPrice = productInputFields[2].getInputValue().toDouble()
        val salesCount = productInputFields[3].getInputValue().toInt()

        return ProductItem.createProduct(item, weight, packaging, discountPrice, salesCount)
    }
}