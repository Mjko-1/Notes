package com.example.notes.main.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.notes.edit.view.EditActivity
import com.example.notes.main.MainNote
import com.example.notes.main.presenter.MainPresenter

class NoteListFragment : Fragment() {

    private var binding: FragmentNoteListBinding? = null

    private lateinit var adapter: YourNotesAdapter

    private var presenter: MainPresenter? = null

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
                presenter?.openNote(it)
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
        adapter.submitList(presenter?.getNoteData())
        (requireActivity() as? MainNote.View)?.displayActionBar(true)
    }

    override fun onDestroyView() {
        binding?.apply {
            list.adapter = null
        }
        presenter = null
        binding = null
        super.onDestroyView()
    }

    companion object {

        @JvmStatic
        fun newInstance() = NoteListFragment()
    }
}