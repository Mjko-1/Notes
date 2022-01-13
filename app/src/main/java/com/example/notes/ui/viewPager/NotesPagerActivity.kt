package com.example.notes.ui.viewPager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.conventions.ActionWithNoteFragment
import com.example.notes.conventions.NoteEditor
import com.example.notes.databinding.ActivityNotesPagerBinding
import com.example.notes.entities.NoteItem
import com.example.notes.model.NoteRepositoryImpl

class NotesPagerActivity : AppCompatActivity(), NoteEditor {

    override var currentFragment: ActionWithNoteFragment? = null

    private var binding: ActivityNotesPagerBinding? = null

    private lateinit var viewModel: NotesPagerViewModel

    private lateinit var adapter: ViewPagerAdapter

    private var startState = true

    private var noteItemId = NoteItem.ID_UNKNOWN
    private var startFragmentItemId = FRAGMENT_ID_UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesPagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(
            this,
            NotesPagerViewModelFactory(NoteRepositoryImpl(this))
        )[NotesPagerViewModel::class.java]

        noteItemId = intent.getLongExtra(EXTRA_NOTE_ITEM_ID, NoteItem.ID_UNKNOWN)

        initViewPager()
        initToolbar()
    }

    override fun onDestroy() {
        currentFragment = null
        binding?.viewPager2?.adapter = null
        binding = null
        super.onDestroy()
    }

    private fun initViewPager() {
        adapter = ViewPagerAdapter(this)

        viewModel.noteList.observe(this) {
            updateData(it)
            if (startState) {
                findStartFragmentIndex()
                binding?.viewPager2?.setCurrentItem(startFragmentItemId, false)
                startState = false
            }
        }
        binding?.viewPager2?.adapter = adapter
    }

    private fun updateData(noteList: List<NoteItem>) {
        adapter.updateData(noteList)
    }

    private fun initToolbar() {
        binding?.apply {
            buttonBack.setOnClickListener {
                onBackPressed()
            }
            buttonSave.setOnClickListener {
                saveNote()
            }
            buttonShare.setOnClickListener {
                shareText()
            }
        }
    }

    private fun findStartFragmentIndex() {
        viewModel.setStartFragmentItemId(noteItemId)
        viewModel.fragmentId.observe(this) {
            startFragmentItemId = it
        }
    }

    companion object {

        private const val EXTRA_NOTE_ITEM_ID = "extra_note_item_id"
        private const val FRAGMENT_ID_UNKNOWN = -1

        fun newIntentEditItem(context: Context, noteItemId: Long): Intent =
            Intent(context, NotesPagerActivity::class.java).apply {
                putExtra(EXTRA_NOTE_ITEM_ID, noteItemId)
            }
    }
}