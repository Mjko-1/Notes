package com.example.notes.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notes.about.AboutActivity
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.list.NoteListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        if (savedInstanceState == null)
            openFragment(binding.fragmentContainer.id, NoteListFragment.newInstance())
    }

    private fun initToolbar() = with(binding) {
        setSupportActionBar(mainActivityToolbar)

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