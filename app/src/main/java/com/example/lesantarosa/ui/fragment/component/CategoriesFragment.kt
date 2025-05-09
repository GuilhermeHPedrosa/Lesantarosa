package com.example.lesantarosa.ui.fragment.component

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_ID_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_REQUEST_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_TITLE_KEY
import com.example.lesantarosa.database.utils.ItemTypeManager.itemType
import com.example.lesantarosa.models.entities.Category
import com.example.lesantarosa.ui.adapter.CategoryAdapter
import com.example.lesantarosa.ui.fragment.page.PageInventoryFragmentDirections
import com.example.lesantarosa.ui.viewmodel.InventoryViewModel
import kotlin.random.Random

class CategoriesFragment: ListFragment<Category>() {

    private val viewModel: InventoryViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var actualSearch: LiveData<String?>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNoteResults()
    }

    override fun initializeFilterFragment() {
        this.filterFragment = SearchFragment(R.string.input_search_category_hint, R.drawable.ic_add, ::navigateToCategoryDialog)
            .also { actualSearch = it.actualSearch }
    }

    override fun initializeRecyclerView() {
        val adapter = CategoryAdapter(requireContext())
        adapter.setItemClick = { navigateToCategoryDialog(it.categoryId, it.title) }

        this.adapter = adapter
    }

    override fun observeListFilters() {
        actualSearch.observe(viewLifecycleOwner) { search ->
            val newSource = viewModel.searchCategories(search)
            updateSource(newSource)
        }
    }

    private fun observeNoteResults() {
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

    private fun navigateToCategoryDialog(categoryId: Long = -1L, title: String? = null) {
        val direction = PageInventoryFragmentDirections.actionPageInventoryToDialogCategory(categoryId, title)
        findNavController().navigate(direction)
    }
}