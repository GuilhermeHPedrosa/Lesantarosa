package com.example.lesantarosa.ui.fragment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesantarosa.R
import com.example.lesantarosa.database.preferences.CartPreferences
import com.example.lesantarosa.databinding.DialogNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class NoteBottomSheetDialogFragment: BottomSheetDialogFragment(), MenuProvider {

    private lateinit var binding: DialogNoteBinding

    private val initialNote by lazy {
        navArgs<NoteBottomSheetDialogFragmentArgs>()
            .value
            .initialNote
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.menu_note_clear -> { handleNoteClearButton() ; true }
            else -> false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeMenuProvider()

        setupNoteText()

        handleSaveButton()
    }

    private fun initializeMenuProvider() {
        initialNote?.let {
            requireActivity()
                .addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }

    private fun setupNoteText() {
        initialNote?.let { binding.noteInputLayout.setText(it) }
    }

    private fun handleSaveButton() {
        val saveNoteButton = binding.saveNoteButton
        saveNoteButton.setOnClickListener {
            lifecycleScope.launch {
                val note = binding.noteInputLayout.text.toString()

                CartPreferences.updateNote(requireContext(), note)
                findNavController().navigateUp()
            }
        }
    }

    private fun handleNoteClearButton() {
        lifecycleScope.launch {
            CartPreferences.updateNote(requireContext(), null)
            findNavController().navigateUp()
        }
    }
}