package com.example.lesantarosa.ui.fragment.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.ItemTypeManager.defineItemType
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.databinding.FragmentDefaultPagerBinding
import com.example.lesantarosa.ui.viewpager.ManagementPagerAdapter
import com.example.lesantarosa.ui.viewmodel.ManagementViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class PageManagementFragment: PageFragment(), MenuProvider {

    private lateinit var binding: FragmentDefaultPagerBinding

    private lateinit var viewModel: ManagementViewModel

    private val args: PageManagementFragmentArgs by navArgs()

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_management, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.menu_remove -> { removeItem() ; true }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defineItemType(args.itemType)

        initializeViewModel()
        initializeTabLayout()
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

        initializeMenuProvider()

        setupViewPager()
        setupTabLayout()
    }

    private fun initializeViewModel() {
        viewModel = getViewModel<ManagementViewModel> { parametersOf(args.itemId, itemType) }
    }

    private fun initializeMenuProvider() {
        args.itemId.takeIf { it != -1L }?.let {
            activity?.addMenuProvider(this, viewLifecycleOwner)
        }
    }

    private fun setupViewPager() {
        val viewPager = binding.viewPagerContainer
        viewPager.adapter = ManagementPagerAdapter(this)
    }

    private fun setupTabLayout() {
        val viewPager = binding.viewPagerContainer
        viewPager.setCurrentItem(args.initialPosition, false)

        TabLayoutMediator(tabLayout!!, viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> R.string.tab_management_manage
                1 -> R.string.tab_management_stock
                else -> throw Exception("Invalid Position")
            }.let { getString(it) }
        }.attach()
    }

    private fun removeItem() {
        viewModel.removeStock {
            Toast.makeText(requireContext(), "Item Removido", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }
}