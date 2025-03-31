package com.example.lesantarosa.models.sealed

import android.text.InputType
import com.example.lesantarosa.models.data.IngredientItem
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.models.data.RecipeItem
import com.example.lesantarosa.models.entities.Item
import com.example.lesantarosa.models.enums.Difficulty
import com.example.lesantarosa.ui.fragment.component.CustomInput
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.random.Random

sealed class ItemInputProvider(item: Item?) {

    protected val itemInputs: MutableList<CustomInput> = mutableListOf(
        CustomInput("Title", "Invalid Title", Regex("^[a-zA-Z0-9\\s!@#\$%^&*()_+\\-=\\[\\]{};':\",./?]{4,}$"), InputType.TYPE_CLASS_TEXT, item?.title),
        CustomInput("Description", "Invalid Description", Regex("^[a-zA-Z]{4,}\$"), InputType.TYPE_CLASS_TEXT, item?.description),
        CustomInput("Price", "Invalid Price", Regex("^\\d+([.,]\\d{1,2})?\$"), (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL), item?.price?.toString()),
        CustomInput("Shelf Life ", "Invalid Shelf Life", Regex("^\\d+$"), InputType.TYPE_CLASS_NUMBER, item?.price?.toString())
    )

    val getItemInputs: List<CustomInput> get() = itemInputs

    protected fun createRawItem(itemId: Long): Item {
        val title = itemInputs[0].getInputValue()
        val description = itemInputs[1].getInputValue()
        val price = itemInputs[2].getInputValue().toDouble()

        val actualDate = Instant.now()
        val expirationDate = actualDate.plus(itemInputs[3].getInputValue().toLong(), ChronoUnit.DAYS)

        return Item(itemId, title, description, null, price, expirationDate.toEpochMilli(), actualDate.toEpochMilli(), null)
    }

    abstract fun createItem(): Item

    class Product(item: Item?) : ItemInputProvider(item) {

        init {
            val productItem = item as? ProductItem
            itemInputs.addAll(listOf(
                CustomInput("Weight", "Invalid Weight", Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, productItem?.discountPrice?.toString()),
                CustomInput("Packaging", "Invalid Packaging", Regex("^[A-Za-z]+\$"), InputType.TYPE_CLASS_TEXT, productItem?.salesCount?.toString()),
                CustomInput("Discount Price", "Invalid Price", Regex("^\\d+([.,]\\d{1,2})?\$"), InputType.TYPE_CLASS_NUMBER, productItem?.discountPrice?.toString()),
                CustomInput("Sales Count", "Invalid Quantity", Regex("^\\d+([.,]\\d{1,2})?\$"), InputType.TYPE_CLASS_NUMBER, productItem?.salesCount?.toString())
            ))
        }

        override fun createItem(): Item {
            val item = createRawItem(Random.nextLong())
            val weight = itemInputs[0].getInputValue().toInt()
            val packaging = itemInputs[1].getInputValue()
            val discountPrice = itemInputs[2].getInputValue().toDouble()
            val salesCount = itemInputs[3].getInputValue().toInt()

            return ProductItem.createProduct(item, weight, packaging, discountPrice, salesCount)
        }
    }

    class Recipe(item: Item?) : ItemInputProvider(item) {

        init {
            val recipeItem = item as? RecipeItem
            itemInputs.addAll(listOf(
                CustomInput("Yield", "Invalid Yield", Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, recipeItem?.servings?.toString()),
                CustomInput("Servings", "Invalid Servings", Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, recipeItem?.yield?.toString()),
                CustomInput("Preparation Time", "Invalid Time", Regex("^\\d+\$"), InputType.TYPE_CLASS_NUMBER, recipeItem?.preparationTime?.toString())
            ))
        }

        override fun createItem(): Item {
            val item = createRawItem(Random.nextLong())
            val yield = itemInputs[0].getInputValue().toInt()
            val servings = itemInputs[1].getInputValue().toInt()
            val preparationTime = itemInputs[2].getInputValue().toInt()

            return RecipeItem.createRecipe(item, yield, servings, preparationTime, Difficulty.MEDIUM)
        }
    }

    class Ingredient(item: Item?) : ItemInputProvider(item) {

        init {
            val ingredientItem = item as? IngredientItem
            itemInputs.addAll(listOf(
                CustomInput("Brand", "Invalid Brand", Regex("^[A-Za-zÀ-ÿ\\s'-]+\$"), InputType.TYPE_CLASS_TEXT, ingredientItem?.brand),
                CustomInput("Supplier", "Invalid Supplier", Regex("^[A-Za-zÀ-ÿ\\s'-]+\$"), InputType.TYPE_CLASS_TEXT, ingredientItem?.supplier),
                CustomInput("Nutritional Info", "Invalid Nutritional", Regex("^\\d+([.,]\\d{1,2})?\$"), InputType.TYPE_CLASS_NUMBER, ingredientItem?.nutritionalInfo)
            ))
        }

        override fun createItem(): Item {
            val item = createRawItem(Random.nextLong())
            val brand = itemInputs[0].getInputValue()
            val supplier = itemInputs[1].getInputValue()
            val nutritionalInfo = itemInputs[2].getInputValue()

            return IngredientItem.createIngredient(item, brand, supplier, nutritionalInfo)
        }
    }

//    protected fun <T> createItem(action: () -> T): T? {
//        return try {
//            action()
//        } catch (e: Exception) {
//            Log.i("", e.message.toString())
//            null
//        }
//    }
}