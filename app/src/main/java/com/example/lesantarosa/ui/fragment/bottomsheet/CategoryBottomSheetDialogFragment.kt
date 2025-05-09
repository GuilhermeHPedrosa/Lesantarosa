package com.example.lesantarosa.ui.fragment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesantarosa.R
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_ID_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_REQUEST_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_TITLE_KEY
import com.example.lesantarosa.databinding.DialogCategoryBinding
import com.example.lesantarosa.ui.fragment.page.PageFragment
import com.example.lesantarosa.ui.viewmodel.InventoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CategoryBottomSheetDialogFragment: BottomSheetDialogFragment() {

    private lateinit var binding: DialogCategoryBinding

    private val args: CategoryBottomSheetDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeMenuProvider()

        setupInitialValue()
        setupInputHint()

        handleSaveButton()
    }

    private fun initializeMenuProvider() {
        val categoryId = args.categoryId.takeIf { it != -1L } ?: return

        object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_category, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_remove -> {
                        setFragmentResult(CATEGORY_REQUEST_KEY, bundleOf(CATEGORY_ID_KEY to categoryId, CATEGORY_TITLE_KEY to null))
                        findNavController().navigateUp()
                    }
                    else -> false
                }
            }
        }.also { requireActivity().addMenuProvider(it, viewLifecycleOwner) }
    }

    private fun setupInitialValue() {
        val actualCategory: String? = args.actualCategory
        actualCategory?.let { binding.categoryEditText.setText(it) }
    }

    private fun setupInputHint() {
        val editText = binding.categoryEditText
        editText.hint = getString(R.string.dialog_category_input_hint)
    }

    private fun handleSaveButton() {
        val saveCategoryButton = binding.saveCategoryButton
        saveCategoryButton.setOnClickListener {
            val categoryId: Long? = args.categoryId.takeIf { it != -1L }
            val categoryTitle = binding.categoryEditText.text?.toString()

            if (categoryTitle.isNullOrBlank()) { return@setOnClickListener }

            setFragmentResult(CATEGORY_REQUEST_KEY, bundleOf(CATEGORY_ID_KEY to categoryId, CATEGORY_TITLE_KEY to categoryTitle))
            findNavController().navigateUp()
        }
    }
}