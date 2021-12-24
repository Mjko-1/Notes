package com.example.notes.viewPager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.notes.conventions.ActionWithNoteFragment
import com.example.notes.databinding.ActivityNotesPagerBinding

class NotesPagerActivity : AppCompatActivity() {

    private var binding: ActivityNotesPagerBinding? = null

    private lateinit var viewModel: NotesPagerViewModel

    private lateinit var adapter: ViewPagerAdapter

    var currentFragment: ActionWithNoteFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesPagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(
            this,
            NotesPagerViewModelFactory(this)
        )[NotesPagerViewModel::class.java]

        initViewPager()
        initToolbar()
    }

    override fun onDestroy() {
        currentFragment = null
        binding?.viewPager2?.adapter = null
        binding = null
        super.onDestroy()
    }

    private fun initToolbar() {
        binding?.apply {
            setSupportActionBar(viewpagerActivityToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            viewpagerActivityToolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            buttonSave.setOnClickListener {
                currentFragment?.tryToSaveText()
            }
            buttonShare.setOnClickListener {
                currentFragment?.tryToShareText()
            }
        }
    }

    private fun initViewPager() {
        adapter = ViewPagerAdapter(this)
        binding?.viewPager2?.adapter = adapter

        binding?.viewPager2?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                currentFragment = supportFragmentManager
                    .findFragmentByTag("f" + binding?.viewPager2?.currentItem)
                        as ActionWithNoteFragment
            }
        })
    }

    companion object {

        private const val EXTRA_NOTE_ITEM_ID = "extra_note_item_id"

        fun newIntentEditItem(context: Context, noteItemId: Long): Intent {
            val intent = Intent(context, NotesPagerActivity::class.java)
            intent.putExtra(EXTRA_NOTE_ITEM_ID, noteItemId)
            return intent
        }
    }
}