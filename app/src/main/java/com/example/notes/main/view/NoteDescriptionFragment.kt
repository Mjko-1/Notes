package com.example.notes.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.NoteItem
import com.example.notes.databinding.FragmentNoteDescriptionBinding

class NoteDescriptionFragment : Fragment() {

    private var binding: FragmentNoteDescriptionBinding? = null

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

        with(binding!!) {
            textTitle.text = note.title
            textText.text = note.text
            textDate.text = note.dateOfCreation
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {

        const val NOTE_TAG = "NoteToFragment"

        @JvmStatic
        fun newInstance() = NoteDescriptionFragment()
    }
}