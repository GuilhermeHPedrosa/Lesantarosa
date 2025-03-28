package com.example.lesantarosa.ui.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lesantarosa.ui.fragment.component.CategoriesFragment
import com.example.lesantarosa.ui.fragment.component.ItemsFragment
import com.example.lesantarosa.ui.fragment.component.StockCatalogFragment

class InventoryPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ItemsFragment()
            1 -> StockCatalogFragment()
            2 -> CategoriesFragment()
            else -> throw IllegalArgumentException("Invalid Position")
        }
    }
}