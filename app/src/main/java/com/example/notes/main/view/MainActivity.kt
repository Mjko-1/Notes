package com.example.notes.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.notes.NoteItem
import com.example.notes.R
import com.example.notes.about.AboutActivity
import com.example.notes.conventions.InteractingWithToolbar
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.edit.view.EditActivity
import com.example.notes.list.NoteListFragment
import com.example.notes.main.MainNote
import com.example.notes.main.presenter.MainPresenter
import com.example.notes.model.NoteDatabase
import com.example.notes.note.NoteDescriptionFragment
import com.example.notes.viewPager.NotesPagerActivity

class MainActivity : AppCompatActivity(), MainNote.View, InteractingWithToolbar {

    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        presenter = MainPresenter(this, NoteDatabase.getInstance(this))

        openFragment(binding.fragmentContainer.id, NoteListFragment.newInstance(), false)

    }

    override fun onDestroy() {
        presenter.view = null
        super.onDestroy()
    }

    private fun initToolbar() = with(binding) {
        setSupportActionBar(mainActivityToolbar)

        buttonAbout.setOnClickListener {
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        }
        buttonOpenViewPager.setOnClickListener {
            startActivity(Intent(this@MainActivity, NotesPagerActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun openFragment(resId: Int, classFragment: Fragment, onStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            if (onStack) addToBackStack(null)
            replace(resId, classFragment)
            commit()
        }
    }

    override fun openEditActivity() {
        startActivity(Intent(this, EditActivity::class.java))
    }

    override fun openNoteDescription(note: NoteItem) {
        displayToolbar(false)
        val bundle = Bundle().apply {
            putParcelable(NoteDescriptionFragment.NOTE_TAG, note)
        }

        val fragmentToManager = NoteDescriptionFragment.newInstance(note)
        fragmentToManager.arguments = bundle

        openFragment(R.id.fragment_container, fragmentToManager, true)
    }

    override fun displayToolbar(condition: Boolean) {
        binding.appBarLayout.isVisible = condition
    }
}