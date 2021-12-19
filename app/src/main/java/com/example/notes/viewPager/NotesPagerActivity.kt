package com.example.notes.viewPager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.notes.conventions.ActionWithNoteFragment
import com.example.notes.databinding.ActivityNotesPagerBinding
import com.example.notes.model.NoteDatabase

class NotesPagerActivity : AppCompatActivity(), NotesPager.View {

    private var binding: ActivityNotesPagerBinding? = null

    private lateinit var adapter: ViewPagerAdapter

    var currentFragment: ActionWithNoteFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesPagerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

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
                currentFragment?.saveNote()
            }
            buttonShare.setOnClickListener {
                currentFragment?.shareNoteText()
            }
        }
    }

    private fun initViewPager() {
        val presenter = ViewPagerPresenter(this, NoteDatabase.getInstance(this))

        adapter = ViewPagerAdapter(this, presenter.getNoteData())
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
}