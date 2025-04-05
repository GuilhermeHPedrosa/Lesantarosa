package com.example.lesantarosa.ui.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lesantarosa.ui.fragment.component.CreationFragment
import com.example.lesantarosa.ui.fragment.component.StockFragment

class ManagementPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> CreationFragment()
            1 -> StockFragment()
            else -> throw IllegalArgumentException("Invalid Position")
        }
    }
}