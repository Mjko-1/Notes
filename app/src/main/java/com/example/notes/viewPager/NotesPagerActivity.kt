package com.example.notes.viewPager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityNotesPagerBinding
import com.example.notes.model.NoteDatabase

class NotesPagerActivity : AppCompatActivity(), NotesPager.View {

    private var binding: ActivityNotesPagerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesPagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initToolbar()
        initViewPager()
    }

    override fun onDestroy() {
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
        }
    }

    private fun initViewPager() {
        val presenter = ViewPagerPresenter(this, NoteDatabase.getInstance(this))
        binding?.viewPager2?.adapter = ViewPagerAdapter(presenter.getNoteData())
    }
}