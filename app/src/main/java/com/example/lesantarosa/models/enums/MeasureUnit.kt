package com.example.lesantarosa.models.enums

enum class MeasureUnit(private val symbol: String) {
    GRAM("g"),
    KILOGRAM("kg"),
    LITER("L"),
    MILLILITER("mL"),
    UNIT("un"),
    CUP("cup"),
    TABLESPOON("tbsp"),
    TEASPOON("tsp");

    override fun toString(): String = symbol

}