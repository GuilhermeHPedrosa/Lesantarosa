package com.example.lesantarosa.models.enums

import android.content.Context
import androidx.annotation.StringRes
import com.example.lesantarosa.R
import java.util.Locale

enum class ItemType(
    @StringRes private val resId: Int
) {
    PRODUCT(R.string.item_type_products),
    RECIPE(R.string.item_type_recipe),
    INGREDIENT(R.string.item_type_ingredients);

    fun getTypeName(context: Context): String {
        return context.getString(resId)
    }
}