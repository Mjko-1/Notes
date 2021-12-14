package com.example.notes.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.notes.NoteItem
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteDescriptionBinding
import com.example.notes.main.MainNote
import com.example.notes.main.presenter.MainPresenter
import com.example.notes.model.NoteDatabase

class NoteDescriptionFragment : Fragment() {

    private var binding: FragmentNoteDescriptionBinding? = null

    private var presenter: MainPresenter? = null

    private var note: NoteItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (arguments != null && arguments?.containsKey(NOTE_TAG) == true) {
            note = arguments?.getParcelable(NOTE_TAG) as? NoteItem?
        }

        return FragmentNoteDescriptionBinding.inflate(inflater).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MainPresenter(
            requireActivity() as? MainNote.View,
            NoteDatabase.getInstance(requireContext())
        )

        initToolbar()

        binding?.apply {
            editTitle.setText(note?.title)
            editText.setText(note?.text)
            textDate.text = note?.dateOfCreation
        }

    }

    override fun onDestroyView() {
        presenter?.view = null
        binding = null
        super.onDestroyView()
    }

    private fun initToolbar() {
        binding?.apply {
            buttonBack.setOnClickListener {
                activity?.onBackPressed()
            }
            buttonSave.setOnClickListener {
                if (editTitle.text.toString() == note?.title &&
                    editText.text.toString() == note?.text
                )
                    presenter?.showMessage(getString(R.string.note_unchanged))
                else note?.let { noteItem ->
                    showDialog(
                        editTitle.text.toString(),
                        editText.text.toString(),
                        noteItem
                    )
                }
            }
            buttonShare.setOnClickListener {
                presenter?.shareNote(editTitle.text.toString(), editText.text.toString())
            }
        }
    }

    private fun showDialog(title: String, text: String, note: NoteItem) {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.edit_dialog_massage))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.positive_button_text)) { _, _ ->
                presenter?.saveNote(title, text, note)
            }
            .setNegativeButton(getString(R.string.negative_button_text)) { negative, _ ->
                negative.dismiss()
            }
            .create()
            .show()
    }

    companion object {

        const val NOTE_TAG = "NoteToFragment"

        @JvmStatic
        fun newInstance() = NoteDescriptionFragment()
    }
}