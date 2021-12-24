package com.example.notes.viewPager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.NoteItem
import com.example.notes.conventions.ActionWithNoteFragment
import com.example.notes.conventions.NoteEditor
import com.example.notes.databinding.ActivityNotesPagerBinding
import com.example.notes.model.NoteRepository
import com.example.notes.model.room.NoteRepositoryImpl

class NotesPagerActivity : AppCompatActivity(), NoteEditor {

    override var currentFragment: ActionWithNoteFragment? = null

    private var binding: ActivityNotesPagerBinding? = null

    private lateinit var viewModel: NotesPagerViewModel

    private lateinit var adapter: ViewPagerAdapter

    private var startState = true

    private val repository: NoteRepository = NoteRepositoryImpl(this)

    private var noteItemId = NoteItem.ID_UNKNOWN
    private var startFragmentItemId = FRAGMENT_ID_UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesPagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(
            this,
            NotesPagerViewModelFactory(repository)
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
            }
        }
        binding?.viewPager2?.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(noteList: List<NoteItem>) {
        adapter.updateData(noteList)
        adapter.notifyDataSetChanged()
    }

    private fun initToolbar() {
        binding?.apply {
            setSupportActionBar(viewpagerActivityToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            viewpagerActivityToolbar.setNavigationOnClickListener {
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

        fun newIntentEditItem(context: Context, noteItemId: Long): Intent {
            val intent = Intent(context, NotesPagerActivity::class.java)
            intent.putExtra(EXTRA_NOTE_ITEM_ID, noteItemId)
            return intent
        }
    }
}