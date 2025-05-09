package com.example.lesantarosa.models.enums

import android.content.Context
import androidx.annotation.StringRes
import com.example.lesantarosa.R

enum class MeasureUnit(
    @StringRes private val resId: Int,
    val symbol: String
) {
    GRAM(R.string.measure_unit_gram, symbol = "g"),
    KILOGRAM(R.string.measure_unit_kilogram, symbol = "kg"),
    LITER(R.string.measure_unit_liter, symbol = "L"),
    MILLILITER(R.string.measure_unit_milliliter, symbol = "mL"),
    UNIT(R.string.measure_unit_unit, symbol = "un"),
    CUP(R.string.measure_unit_cup, symbol = "cup"),
    TABLESPOON(R.string.measure_unit_tablespoon, symbol = "tbsp"),
    TEASPOON(R.string.measure_unit_teaspoon, symbol = "tsp");

    fun getUnitName(context: Context): String {
        return context.getString(resId)
    }
}