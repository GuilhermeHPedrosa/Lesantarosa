package com.example.lesantarosa.ui.fragment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_ID_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_REQUEST_KEY
import com.example.lesantarosa.database.utils.FragmentKeys.CATEGORY_TITLE_KEY
import com.example.lesantarosa.databinding.DialogCategoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

        setupCategoryTitle()

        handleSaveButton()
    }

    private fun setupCategoryTitle() {
        val categoryTitle: String? = args.categoryTitle
        categoryTitle?.let { binding.categoryEditText.setText(it) }
    }

    private fun handleSaveButton() {
        val saveCategoryButton = binding.saveCategoryButton
        saveCategoryButton.setOnClickListener {
            val categoryId: Long? = args.categoryId.takeIf { it != 0L }
            val categoryTitle = binding.categoryEditText.text?.toString()

            setFragmentResult(CATEGORY_REQUEST_KEY, bundleOf(CATEGORY_ID_KEY to categoryId, CATEGORY_TITLE_KEY to categoryTitle))
            findNavController().navigateUp()
        }
    }
}