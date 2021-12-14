package com.example.notes.edit.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.databinding.ActivityEditBinding
import com.example.notes.edit.EditNote
import com.example.notes.edit.presenter.EditPresenter
import com.example.notes.model.NoteDatabase

class EditActivity : AppCompatActivity(), EditNote.View {

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
            showDialog(editText.text.toString(), editText.text.toString())
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

    private fun showDialog(title: String, text: String) {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.edit_dialog_massage))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.positive_button_text)) { _, _ ->
                presenter.saveNote(title, text)
            }
            .setNegativeButton(getString(R.string.negative_button_text)) { negative, _ ->
                negative.dismiss()
            }
            .create()
            .show()
    }
}

