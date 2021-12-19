package com.example.notes.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
    }

    private fun initToolbar() = with(binding) {
        setSupportActionBar(aboutActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        aboutActivityToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}