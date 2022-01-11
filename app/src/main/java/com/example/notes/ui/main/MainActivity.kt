package com.example.notes.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notes.app.MyApp
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.model.NetRepository
import com.example.notes.model.NoteRepositoryImpl
import com.example.notes.ui.about.AboutActivity
import com.example.notes.ui.list.NoteListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(
                NoteRepositoryImpl(this),
                NetRepository((application as MyApp).noteApi)
            )
        )[MainViewModel::class.java]

        initToolbar()

        if (savedInstanceState == null)
            openFragment(binding.fragmentContainer.id, NoteListFragment.newInstance())
    }

    private fun initToolbar() = with(binding) {
        setSupportActionBar(mainActivityToolbar)

        buttonDownload.setOnClickListener {
            viewModel.getNote()
            if (viewModel.noteFromFirebase.value == null) {
                viewModel.noteFromFirebase.observe(this@MainActivity) {
                    viewModel.saveNote(it)
                }
            }
        }

        buttonAbout.setOnClickListener {
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        }
    }

    private fun openFragment(resId: Int, classFragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(resId, classFragment)
            commit()
        }
    }
}