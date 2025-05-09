package com.example.lesantarosa.ui.fragment

import android.content.Context
import com.example.lesantarosa.models.data.IngredientItem
import com.example.lesantarosa.models.entities.Ingredient
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.models.entities.Product
import com.example.lesantarosa.models.data.RecipeItem
import com.example.lesantarosa.models.entities.Recipe
import com.example.lesantarosa.models.data.SaleSummary
import com.example.lesantarosa.models.data.StockItem
import com.example.lesantarosa.models.enums.MeasureUnit

fun Double.formatPrice(): String = "R$ %.2f".format(this)
fun Int.formatWeight() = "${this} gramas"

fun ProductItem.formatEntity() = Product(0L, itemId, weight, packaging, salesCount)
fun RecipeItem.formatEntity() = Recipe(0L, this.itemId, this.yield, this.servings, preparationTime, difficulty)
fun IngredientItem.formatEntity() = Ingredient(0L, this.itemId, this.brand, this.supplier, this.nutritionalInfo)