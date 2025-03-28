package com.example.lesantarosa.ui.fragment.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.ItemTypeManager.defineItemType
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.databinding.FragmentDefaultPagerBinding
import com.example.lesantarosa.models.data.VisualComponents
import com.example.lesantarosa.ui.adapter.viewpager.InventoryPagerAdapter
import com.example.lesantarosa.ui.viewmodel.InventoryViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class PageInventoryFragment: PageFragment() {

    private lateinit var binding: FragmentDefaultPagerBinding

    private lateinit var viewModel: InventoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.defineVisualComponents(VisualComponents(tabLayout = true))

        defineItemType(navArgs<PageInventoryFragmentArgs>().value.itemType)

        viewModel = getViewModel<InventoryViewModel> { parametersOf(itemType) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        val viewPager = binding.viewPagerContainer
        viewPager.adapter = InventoryPagerAdapter(this)
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tabLayout, binding.viewPagerContainer) { tab, position ->
            tab.text = when(position) {
                0 -> R.string.tab_inventory_items
                1 -> R.string.tab_inventory_stocks
                2 -> R.string.tab_inventory_categories
                else -> throw IllegalArgumentException("Invalid Position")

            }.let { getString(it) }
        }.attach()
    }
}