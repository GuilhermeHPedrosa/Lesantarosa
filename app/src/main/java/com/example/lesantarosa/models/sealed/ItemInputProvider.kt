package com.example.lesantarosa.models.sealed

import android.text.InputType
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.models.data.IngredientItem
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.models.data.RecipeItem
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.enums.Difficulty
import com.example.lesantarosa.ui.fragment.component.CustomInput
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.random.Random

sealed class ItemInputProvider(private val item: Item?) {

    private val itemInputs: List<CustomInput> = mutableListOf(
        CustomInput(R.string.input_item_title_hint, R.string.input_item_title_error, Regex("^[a-zA-Z0-9\\s!@#\$%^&*()_+\\-=\\[\\]{};':\",./?]{4,}$"), InputType.TYPE_CLASS_TEXT, item?.title),
        CustomInput(R.string.input_item_description_hint, R.string.input_item_description_hint, Regex("^[a-zA-Z]{4,}\$"), InputType.TYPE_CLASS_TEXT, item?.description),
        CustomInput(R.string.input_item_price_hint, R.string.input_item_price_error, Regex("^\\d+([.,]\\d{1,2})?\$"), (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL), item?.price?.toString()),
        CustomInput(R.string.input_item_shelf_life_hint, R.string.input_item_shelf_life_error, Regex("^\\d+$"), InputType.TYPE_CLASS_NUMBER, item?.expirationDate?.toString())
    )

    protected val childInputs: MutableList<CustomInput> = mutableListOf()

    val getItemInputs: List<CustomInput> get() = itemInputs
    val getChildInputs: List<CustomInput> get() = childInputs

    protected fun createRawItem(): Item {
        val itemId = item?.itemId ?: Random.nextLong()
        val title = itemInputs[0].getInputValue()
        val description = itemInputs[1].getInputValue()
        val price = itemInputs[2].getInputValue().toDouble()

        val actualDate = Instant.now()
        val expirationDate = actualDate.plus(itemInputs[3].getInputValue().toLong(), ChronoUnit.DAYS).toEpochMilli()

        val createdAt = item?.createdAt ?: actualDate.toEpochMilli()
        val updatedAt = actualDate.toEpochMilli()

        return Item(itemId, title, description, null, price, expirationDate, createdAt, updatedAt, itemType)
    }

    abstract fun createItem(): Item

    // ===================== Product =====================

    class Product(item: Item?) : ItemInputProvider(item) {

        private var productItem: ProductItem? = item as? ProductItem

        init {
            childInputs.addAll(listOf(
                CustomInput(R.string.input_product_weight_hint, R.string.input_product_weight_error, Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, productItem?.weight?.toString()),
                CustomInput(R.string.input_product_packaging_hint, R.string.input_product_packaging_error, Regex("^[A-Za-z]+\$"), InputType.TYPE_CLASS_TEXT, productItem?.packaging),
                CustomInput(R.string.input_product_sales_count_hint, R.string.input_product_sales_count_error, Regex("^\\d+([.,]\\d{1,2})?\$"), InputType.TYPE_CLASS_NUMBER, productItem?.salesCount?.toString())
            ))
        }

        override fun createItem(): ProductItem {
            val item = createRawItem()
            val weight = childInputs[0].getInputValue().toInt()
            val packaging = childInputs[1].getInputValue()
            val salesCount = childInputs[2].getInputValue().toInt()

            return ProductItem.createProduct(item, weight, packaging, salesCount)
        }
    }

    // ===================== Recipe =====================

    class Recipe(item: Item?) : ItemInputProvider(item) {

        private val recipeItem = item as? RecipeItem

        init {
            childInputs.addAll(listOf(
                CustomInput(R.string.input_recipe_yield_hint, R.string.input_recipe_yield_error, Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, recipeItem?.servings?.toString()),
                CustomInput(R.string.input_recipe_servings_hint, R.string.input_recipe_servings_error, Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, recipeItem?.yield?.toString()),
                CustomInput(R.string.input_recipe_preparation_time_hint, R.string.input_recipe_preparation_time_error, Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, recipeItem?.preparationTime?.toString())
            ))
        }

        override fun createItem(): RecipeItem {
            val item = createRawItem()
            val yield = childInputs[0].getInputValue().toInt()
            val servings = childInputs[1].getInputValue().toInt()
            val preparationTime = childInputs[2].getInputValue().toInt()

            return RecipeItem.createRecipe(item, yield, servings, preparationTime, Difficulty.MEDIUM)
        }
    }

    // ===================== Ingredient =====================

    class Ingredient(item: Item?) : ItemInputProvider(item) {

        private val ingredientItem = item as? IngredientItem

        init {
            childInputs.addAll(listOf(
                CustomInput(R.string.input_ingredient_brand_hint, R.string.input_ingredient_brand_error, Regex("^[A-Za-zÀ-ÿ\\s'-]+\$"), InputType.TYPE_CLASS_TEXT, ingredientItem?.brand),
                CustomInput(R.string.input_ingredient_supplier_hint, R.string.input_ingredient_supplier_error, Regex("^[A-Za-zÀ-ÿ\\s'-]+\$"), InputType.TYPE_CLASS_TEXT, ingredientItem?.supplier),
                CustomInput(R.string.input_ingredient_nutritional_info_hint, R.string.input_ingredient_nutritional_info_error, Regex("^\\d+([.,]\\d{1,2})?\$"), InputType.TYPE_CLASS_NUMBER, ingredientItem?.nutritionalInfo)
            ))
        }

        override fun createItem(): IngredientItem {
            val item = createRawItem()
            val brand = childInputs[0].getInputValue()
            val supplier = childInputs[1].getInputValue()
            val nutritionalInfo = childInputs[2].getInputValue()

            return IngredientItem.createIngredient(item, brand, supplier, nutritionalInfo)
        }
    }
}