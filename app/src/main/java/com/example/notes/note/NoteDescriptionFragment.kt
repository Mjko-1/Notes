package com.example.notes.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.notes.entities.NoteItem
import com.example.notes.R
import com.example.notes.conventions.ActionWithNoteFragment
import com.example.notes.conventions.NoteEditor
import com.example.notes.databinding.FragmentNoteDescriptionBinding
import com.example.notes.dialogs.SaveConfirmationDialog
import com.example.notes.model.NoteRepositoryImpl
import com.example.notes.opportunities.shareText
import com.example.notes.viewPager.NotesPagerActivity

class NoteDescriptionFragment : Fragment(), ActionWithNoteFragment {

    private var binding: FragmentNoteDescriptionBinding? = null

    private lateinit var viewModel: NoteDescriptionViewModel

    private var screenMode = MODE_UNKNOWN
    private var noteItemId = NoteItem.ID_UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParameters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNoteDescriptionBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            NoteDescriptionViewModelFactory(NoteRepositoryImpl(requireContext()))
        )[NoteDescriptionViewModel::class.java]

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        observeViewModel()
        launchRightMode()
        setupFragmentResultListener()
        setupOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        if (activity is NoteEditor) (activity as NoteEditor).currentFragment = this
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun tryToShareText() {
        binding?.apply {
            viewModel?.shareNote(editTitle.text.toString(), editText.text.toString())
        }
    }

    override fun tryToSaveText() {
        binding?.apply {
            viewModel?.setNoteText(editText.text.toString())
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
            noteItemId = args.getLong(NOTE_ITEM_ID, NoteItem.ID_UNKNOWN)
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
        requireContext().shareText(text)
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.setNoteItem(noteItemId)
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
                    if (screenMode == MODE_ADD) {
                        var startActivity = true
                        viewModel?.noteList?.observe(viewLifecycleOwner) {
                            if (startActivity) {
                                startActivity(
                                    NotesPagerActivity.newIntentEditItem(
                                        requireContext(),
                                        it.last().id
                                    )
                                )
                                startActivity = false
                                requireActivity().finish()
                            }
                        }
                        viewModel?.addNoteItem(editTitle.text.toString(), editText.text.toString())
                    }
                    if (screenMode == MODE_EDIT) {
                        viewModel?.editNoteItem(editTitle.text.toString(), editText.text.toString())
                        showMessage(getString(R.string.saving_successfully))
                    }
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

        const val CONFIRMATION_TAG = "confirmation"
        const val AGREE_TAG = "agree"

        fun newInstanceAddNote(): NoteDescriptionFragment = NoteDescriptionFragment().apply {
            arguments = Bundle().apply {
                putString(SCREEN_MODE, MODE_ADD)
            }
        }

        fun newInstanceEditNote(noteItemId: Long): NoteDescriptionFragment =
            NoteDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putLong(NOTE_ITEM_ID, noteItemId)
                }
            }
    }
}