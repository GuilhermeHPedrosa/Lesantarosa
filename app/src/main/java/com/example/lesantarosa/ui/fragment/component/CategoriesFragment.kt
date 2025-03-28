package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_ID_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_REQUEST_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_TITLE_KEY
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.models.entities.Category
import com.example.lesantarosa.ui.adapter.recyclerview.CategoryAdapter
import com.example.lesantarosa.ui.fragment.page.PageInventoryFragmentDirections
import com.example.lesantarosa.ui.viewmodel.InventoryViewModel
import kotlin.random.Random

class CategoriesFragment: ListFragment<Category>() {

    private val viewModel: InventoryViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configNoteResultListener()
    }

    override fun initializeFilterFragment() {
        val searchFragment = SearchFragment()
            .also { setupSearchIcon(it) ; observeSearchUpdates(it) }

        this.filterFragment = searchFragment
    }

    override fun initializeRecyclerView() {
        val adapter = CategoryAdapter(requireContext())
        adapter.setItemClick = { categoryId, title ->
            navigateToCategoryDialog(categoryId, title)
        }

        this.adapter = adapter
    }

    private fun setupSearchIcon(filterFragment: SearchFragment) {
        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_add)?.let {
            filterFragment.setupIconValues(it, 1) {
                navigateToCategoryDialog()
            }
        }
    }

    private fun observeSearchUpdates(filterFragment: SearchFragment) {
        filterFragment.actualSearch.observe(viewLifecycleOwner) { search ->
            updateSource(viewModel.searchCategories(search))
        }
    }

    private fun navigateToCategoryDialog(categoryId: Long = -1L, title: String? = null) {
        val direction = PageInventoryFragmentDirections.actionPageInventoryToDialogCategory(categoryId, title)
        findNavController().navigate(direction)
    }

    private fun configNoteResultListener() {
        requireParentFragment().setFragmentResultListener(CATEGORY_REQUEST_KEY) { _, bundle ->
            val categoryId: Long? = bundle.getLong(CATEGORY_ID_KEY, -1L).takeIf { it != -1L }
            val title: String? = bundle.getString(CATEGORY_TITLE_KEY)

            if (title != null) {
                val category = Category(categoryId ?: Random.nextLong(), title, itemType)
                viewModel.saveCategory(category)

            } else if (categoryId != null) {
                viewModel.removeCategory(categoryId)
            }
        }
    }
}