package com.example.notes.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.NoteItem
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteDescriptionBinding
import com.example.notes.main.MainNote
import com.example.notes.main.presenter.MainPresenter

class NoteDescriptionFragment : Fragment() {

    private var binding: FragmentNoteDescriptionBinding? = null

    private var presenter: MainPresenter? = null

    private lateinit var note: NoteItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (arguments != null && requireArguments().containsKey(NOTE_TAG)) {
            note = requireArguments().getSerializable(NOTE_TAG) as NoteItem
        }

        binding = FragmentNoteDescriptionBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MainPresenter(requireActivity() as? MainNote.View)

        toolbarAction()

        with(binding!!) {
            editTitle.setText(note.title)
            editText.setText(note.text)
            textDate.text = note.dateOfCreation
        }
    }

    override fun onDestroyView() {
        presenter?.view = null
        binding = null
        super.onDestroyView()
    }

    private fun toolbarAction() {
        binding?.apply {
            toolbar.setNavigationIcon(R.drawable.ic_back)
            toolbar.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.buttonSave -> {
                        presenter?.saveNote(
                            editTitle.text.toString(),
                            editText.text.toString(),
                            note
                        )
                        true
                    }
                    R.id.buttonShare -> {
                        presenter?.shareNote(editTitle.text.toString(), editText.text.toString())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    companion object {

        const val NOTE_TAG = "NoteToFragment"

        @JvmStatic
        fun newInstance() = NoteDescriptionFragment()
    }
}