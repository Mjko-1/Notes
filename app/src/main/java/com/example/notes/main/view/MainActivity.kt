package com.example.notes.main.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notes.FragmentOpener
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.InteractsWithHomeButton
import com.example.notes.main.MainNote
import com.example.notes.main.presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainNote.View, FragmentOpener, InteractsWithHomeButton {

    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter(this)

        openFragment(binding.fragmentContainer.id, NoteListFragment.newInstance(), false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
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

    override fun showHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}