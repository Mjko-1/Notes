package com.example.notes.ui.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.databinding.ActivityEditBinding
import com.example.notes.ui.note.NoteDescriptionFragment

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.note_item_container, NoteDescriptionFragment.newInstanceAddNote())
                .commit()
    }
}

