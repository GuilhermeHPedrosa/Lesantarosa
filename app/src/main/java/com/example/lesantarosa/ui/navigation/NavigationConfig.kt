package com.example.lesantarosa.ui.navigation

import com.example.lesantarosa.R
import com.example.lesantarosa.NavGraphDirections
import com.example.lesantarosa.models.enums.ItemType.PRODUCT
import com.example.lesantarosa.models.enums.ItemType.RECIPE
import com.example.lesantarosa.models.enums.ItemType.INGREDIENT

object NavigationConfig {

    val drawerDestinations = setOf(
        R.id.fragment_page_home,
        R.id.fragment_page_sell,
        R.id.fragment_page_orders,
        R.id.fragment_page_inventory
    )

    val drawerNavigationActions  = mapOf(
        R.id.menu_home to NavGraphDirections.actionGlobalToPageHome(),
        R.id.menu_sell to NavGraphDirections.actionGlobalToPageSell(),
        R.id.menu_orders to NavGraphDirections.actionGlobalToPageOrders(),
        R.id.menu_products to NavGraphDirections.actionGlobalToPageInventory(PRODUCT.name),
        R.id.menu_recipes to NavGraphDirections.actionGlobalToPageInventory(RECIPE.name),
        R.id.menu_ingredients to NavGraphDirections.actionGlobalToPageInventory(INGREDIENT.name)
    )
}