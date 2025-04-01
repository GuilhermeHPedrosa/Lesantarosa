package com.example.lesantarosa.ui.fragment

import com.example.lesantarosa.models.data.IngredientItem
import com.example.lesantarosa.models.entities.Ingredient
import com.example.lesantarosa.models.data.ProductItem
import com.example.lesantarosa.models.entities.Product
import com.example.lesantarosa.models.data.RecipeItem
import com.example.lesantarosa.models.entities.Recipe
import com.example.lesantarosa.models.data.SaleSummary

fun Double.formatPrice() = "R$ $this"
fun Int.formatStock() = "Stock: $this"

fun SaleSummary.formatValues() = "$totalQuantity items -> ${totalPrice.formatPrice()}"

fun ProductItem.formatEntity() = Product(0L, this.itemId, this.weight, this.packaging, this.salesCount)
fun RecipeItem.formatEntity() = Recipe(0L, this.itemId, this.yield, this.servings, this.preparationTime, this.difficulty)
fun IngredientItem.formatEntity() = Ingredient(0L, this.itemId, this.brand, this.supplier, this.nutritionalInfo)