package com.example.notes.main.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notes.NoteItem
import com.example.notes.R
import com.example.notes.about.AboutActivity
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.edit.view.EditActivity
import com.example.notes.main.MainNote
import com.example.notes.main.presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainNote.View {

    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        presenter = MainPresenter(this)

        openFragment(binding.fragmentContainer.id, NoteListFragment.newInstance(), false)
    }

    override fun onDestroy() {
        presenter.view = null
        super.onDestroy()
    }

    private fun initActionBar() {
        supportActionBar?.title = getString(R.string.main_activity_actionbar_text)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.buttonAbout -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return true
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

    override fun showMessage(massage: String) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show()
    }

    override fun showMessageEmpty() {
        Toast.makeText(this, R.string.empty_text_massage, Toast.LENGTH_SHORT).show()
    }

    override fun shareText(noteText: String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, noteText)
        })
    }

    override fun displayActionBar(condition: Boolean) {
        if (condition) supportActionBar?.show()
        else supportActionBar?.hide()
    }

    override fun openEditActivity() {
        startActivity(Intent(this, EditActivity::class.java))
    }

    override fun openNoteDescription(note: NoteItem) {
        displayActionBar(false)
        val bundle = Bundle().apply {
            putSerializable(NoteDescriptionFragment.NOTE_TAG, note)
        }

        val fragmentToManager = NoteDescriptionFragment.newInstance()
        fragmentToManager.arguments = bundle

        openFragment(R.id.fragment_container, fragmentToManager, true)
    }
}