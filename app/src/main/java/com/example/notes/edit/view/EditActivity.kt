package com.example.notes.edit.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.conventions.SaverActivity
import com.example.notes.databinding.ActivityEditBinding
import com.example.notes.dialogs.SaveConfirmationDialog
import com.example.notes.edit.EditNote
import com.example.notes.edit.presenter.EditPresenter
import com.example.notes.model.NoteDatabase

class EditActivity : AppCompatActivity(), EditNote.View, SaverActivity {

    private lateinit var binding: ActivityEditBinding

    private lateinit var presenter: EditPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = EditPresenter(this, NoteDatabase.getInstance(this))

        initToolbar()
    }

    private fun initToolbar() = with(binding) {
        setSupportActionBar(editActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editActivityToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        buttonShare.setOnClickListener {
            presenter.shareNote(editText.text.toString(), editText.text.toString())
        }
        buttonSave.setOnClickListener {
            SaveConfirmationDialog().show(supportFragmentManager, "Alert dialog")
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

    override fun saveEdit() = with(binding) {
        presenter.saveNote(editText.text.toString(), editText.text.toString())
    }
}

