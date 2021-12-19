package com.example.notes.note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.notes.NoteItem
import com.example.notes.R
import com.example.notes.conventions.ActionWithNoteFragment
import com.example.notes.databinding.FragmentNoteDescriptionBinding
import com.example.notes.dialogs.SaveConfirmationDialog
import com.example.notes.model.NoteDatabase
import com.example.notes.viewPager.NotesPagerActivity

class NoteDescriptionFragment : Fragment(), NoteDescription.View, ActionWithNoteFragment {

    private var binding: FragmentNoteDescriptionBinding? = null

    private var presenter: NoteDescriptionPresenter? = null

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

        (activity as? NotesPagerActivity)?.currentFragment = this

        presenter = NoteDescriptionPresenter(this, NoteDatabase.getInstance(requireContext()))

        initToolbar()

        binding?.apply {
            editTitle.setText(note?.title)
            editText.setText(note?.text)
            textDate.text = note?.dateOfCreation

            setFragmentResultListener(CONFIRMATION_TAG) { _, bundle ->
                val result = bundle.getBoolean(AGREE_TAG)
                if (result) note?.let {
                    presenter?.saveNote(
                        editTitle.text.toString(), editText.text.toString(),
                        it
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? NotesPagerActivity)?.currentFragment = this
    }

    override fun onDestroyView() {
        presenter?.view = null
        binding = null
        super.onDestroyView()
    }

    override fun saveNote() {
        note?.let {
            presenter?.tryToSaveNote(
                binding?.editTitle?.text.toString(),
                binding?.editText?.text.toString(),
                it
            )
        }
    }

    override fun shareNoteText() {
        presenter?.shareNote(
            binding?.editTitle?.text.toString(),
            binding?.editText?.text.toString()
        )
    }

    override fun shareText(noteText: String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, noteText)
        })
    }

    override fun showMessage(massage: String) {
        Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()
    }

    override fun showMessageEmpty() {
        Toast.makeText(requireContext(), R.string.empty_text_massage, Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        binding?.apply {
            buttonBack.setOnClickListener {
                activity?.onBackPressed()
            }

            buttonSave.setOnClickListener {
                note?.let { noteItem ->
                    presenter?.tryToSaveNote(
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

    override fun showDialog() {
        SaveConfirmationDialog().show(parentFragmentManager, "Alert dialog")
    }

    companion object {

        const val NOTE_TAG = "NoteToFragment"
        const val CONFIRMATION_TAG = "NoteToFragment"
        const val AGREE_TAG = "NoteToFragment"

        @JvmStatic
        fun newInstance(noteItem: NoteItem) = NoteDescriptionFragment().apply {
            arguments = Bundle().apply {
                putParcelable(NOTE_TAG, noteItem)
            }
        }
    }
}



