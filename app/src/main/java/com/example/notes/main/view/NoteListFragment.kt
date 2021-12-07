package com.example.notes.main.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.FragmentOpener
import com.example.notes.InteractsWithHomeButton
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.notes.edit.view.EditActivity
import com.example.notes.main.MainNote
import com.example.notes.main.presenter.MainPresenter

class NoteListFragment : Fragment() {

    private var binding: FragmentNoteListBinding? = null

    private lateinit var adapter: YourNotesAdapter

    private lateinit var presenter: MainPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            presenter = MainPresenter(requireActivity() as? MainNote.View)

            adapter = YourNotesAdapter {
                (activity as InteractsWithHomeButton).showHomeButton()
                val bundle = Bundle().apply {
                    putSerializable(NoteDescriptionFragment.NOTE_TAG, it)
                }

                val fragmentToManager = NoteDescriptionFragment.newInstance()
                fragmentToManager.arguments = bundle

                (activity as FragmentOpener).openFragment(
                    R.id.fragment_container,
                    fragmentToManager,
                    true
                )
            }

            list.adapter = adapter

            fabAddNote.setOnClickListener {
                requireActivity().startActivity(
                    Intent(
                        activity?.baseContext,
                        EditActivity::class.java
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(presenter.getNoteData())
    }

    override fun onDestroyView() {
        binding?.apply {
            list.adapter = null
        }
        binding = null
        super.onDestroyView()
    }

    companion object {

        @JvmStatic
        fun newInstance() = NoteListFragment()
    }
}