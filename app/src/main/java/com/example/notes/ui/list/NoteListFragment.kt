package com.example.notes.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.notes.ui.edit.EditActivity
import com.example.notes.model.NoteRepositoryImpl
import com.example.notes.ui.viewPager.NotesPagerActivity

class NoteListFragment : Fragment() {

    private var binding: FragmentNoteListBinding? = null

    private lateinit var viewModel: NoteListViewModel

    private lateinit var adapter: YourNotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNoteListBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            NoteListViewModelFactory(NoteRepositoryImpl(requireContext()))
        )[NoteListViewModel::class.java]

        setupRecyclerView()
        setupOnClickListeners()
        observeViewModel()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setupRecyclerView() {
        binding?.apply {
            adapter = YourNotesAdapter()

            list.adapter = adapter

            setupClickListenerToAdapter()
            setupSwipeListener(list)
        }
    }

    private fun setupClickListenerToAdapter() {
        adapter.onClick = {
            startActivity(NotesPagerActivity.newIntentEditItem(requireContext(), it))
        }
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteNoteItem(item.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupOnClickListeners() {
        binding?.apply {
            fabAddNote.setOnClickListener {
                startActivity(Intent(requireContext(), EditActivity::class.java))
            }
        }
    }

    private fun observeViewModel() {
        viewModel.noteList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = NoteListFragment()
    }
}