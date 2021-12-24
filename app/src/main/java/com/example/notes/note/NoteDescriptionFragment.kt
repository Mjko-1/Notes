package com.example.notes.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.about.opportunities.SharingText
import com.example.notes.conventions.ActionWithNoteFragment
import com.example.notes.databinding.FragmentNoteDescriptionBinding
import com.example.notes.dialogs.SaveConfirmationDialog

class NoteDescriptionFragment : Fragment(), ActionWithNoteFragment {

    private var binding: FragmentNoteDescriptionBinding? = null

    private lateinit var viewModel: NoteDescriptionViewModel

    private val sharingText = SharingText()

    private var screenMode = MODE_UNKNOWN
    private var noteItemId = ID_UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParameters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentNoteDescriptionBinding.inflate(inflater).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            NoteDescriptionViewModelFactory(requireContext())
        )[NoteDescriptionViewModel::class.java]

        observeViewModel()
        launchRightMode()
        setupFragmentResultListener()
        setupOnClickListeners()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun tryToShareText() {
        binding?.apply {
            viewModel.shareNote(editTitle.text.toString(), editText.text.toString())
        }
    }

    override fun tryToSaveText() {
        binding?.apply {
            viewModel.setNoteText(editText.text.toString())
        }
    }

    private fun parseParameters() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD)
            throw RuntimeException("Unknown screen mode: $mode")
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(NOTE_ITEM_ID))
                throw RuntimeException("Param noteItemId is absent")
            noteItemId = args.getLong(NOTE_ITEM_ID, ID_UNKNOWN)
        }
    }

    private fun observeViewModel() {
        viewModel.textToShare.observe(viewLifecycleOwner) {
            if (it.isEmpty()) showMessage(getString(R.string.empty_note_text))
            else shareText(it)
        }

        viewModel.noteText.observe(viewLifecycleOwner) {
            if (it.isEmpty()) showMessage(getString(R.string.empty_note_text))
            else showDialog()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showDialog() {
        SaveConfirmationDialog().show(parentFragmentManager, "Alert dialog")
    }

    private fun shareText(text: String) {
        sharingText.shareText(text, requireContext())
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        binding?.apply {
            viewModel.setNoteItem(noteItemId)
            viewModel.noteItem.observe(viewLifecycleOwner) {
                editTitle.setText(it.title)
                editText.setText(it.text)
                textDate.text = it.dateOfCreation
            }
        }
    }

    private fun launchAddMode() {
        binding?.apply {
            buttonSave.setOnClickListener {
                tryToSaveText()
            }

            buttonShare.setOnClickListener {
                tryToShareText()
            }
        }
    }

    private fun setupFragmentResultListener() {
        binding?.apply {
            setFragmentResultListener(CONFIRMATION_TAG) { _, bundle ->
                val result = bundle.getBoolean(AGREE_TAG)
                if (result) {
                    if (screenMode == MODE_ADD)
                        viewModel.addNoteItem(editTitle.text.toString(), editText.text.toString())
                    if (screenMode == MODE_EDIT)
                        viewModel.editNoteItem(editTitle.text.toString(), editText.text.toString())
                    showMessage(getString(R.string.saving_successfully))
                }
            }
        }
    }

    private fun setupOnClickListeners() {
        binding?.apply {
            buttonBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    companion object {

        private const val NOTE_ITEM_ID = "note_item_id"
        private const val SCREEN_MODE = "mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
        private const val ID_UNKNOWN: Long = -1

        const val CONFIRMATION_TAG = "confirmation"
        const val AGREE_TAG = "agree"

        fun newInstanceAddNote(): NoteDescriptionFragment {
            return NoteDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditNote(noteItemId: Long): NoteDescriptionFragment {
            return NoteDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putLong(NOTE_ITEM_ID, noteItemId)
                }
            }
        }
    }
}