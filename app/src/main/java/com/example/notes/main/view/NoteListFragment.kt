package com.example.notes.main.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.notes.edit.view.EditActivity
import com.example.notes.main.presenter.MainPresenter

class NoteListFragment : Fragment() {

    private lateinit var binding: FragmentNoteListBinding

    private lateinit var adapter: YourNotesAdapter

    private lateinit var presenter: MainPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            presenter = MainPresenter(activity as MainActivity)

            updateUi()

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

    /**
     * Как лучше организовать обновление данных?
     *
     * На данный момент это неоптимизированный костыль.
     * */
    override fun onStart() {
        super.onStart()
        updateUi()
    }

    private fun updateUi() = with(binding) {
        adapter = YourNotesAdapter(presenter.getNoteData(), activity as MainActivity)
        list.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = NoteListFragment()
    }
}