package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.text.InputType
import android.view.View
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.data.RecipeItem
import com.example.lesantarosa.models.enums.Difficulty

class CreationRecipeFragment: CreationFragment() {

    private lateinit var recipeInputFields: List<CustomInput>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getItem {
            initializeItemFields(it)
            initializeItemsInputLayout()
        }
    }

    override fun initializeItemFields(item: Item?) {
        super.initializeItemFields(item)

        val recipe = item as? RecipeItem
        recipeInputFields = listOf(
            CustomInput("Yield", "Invalid Yield", Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, recipe?.servings?.toString()),
            CustomInput("Servings", "Invalid Servings", Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, recipe?.yield?.toString()),
            CustomInput("Preparation Time", "Invalid Time", Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, recipe?.preparationTime?.toString())
        )
    }

    override fun initializeItemsInputLayout() {
        super.initializeItemsInputLayout()

        val inputFieldsLinearLayout = binding.entityCreationLayout.inputFieldsLinearLayout
        recipeInputFields.forEach { inputFieldsLinearLayout.addView(it.createInputLayout(requireContext())) }
    }

    override fun createItem(): Item {
        val item = createRawItem()
        val yield = recipeInputFields[0].getInputValue().toInt()
        val servings = recipeInputFields[1].getInputValue().toInt()
        val preparationTime = recipeInputFields[2].getInputValue().toInt()

        return RecipeItem.createRecipe(item, yield, servings, preparationTime, Difficulty.MEDIUM)
    }
}