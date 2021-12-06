package com.example.notes.edit.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.databinding.ActivityEditBinding
import com.example.notes.edit.EditNote
import com.example.notes.edit.presenter.EditPresenter

class EditActivity : AppCompatActivity(), EditNote.View {

    private lateinit var binding: ActivityEditBinding

    private lateinit var presenter: EditPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        presenter = EditPresenter(this)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.edit_activity_actionbar_text)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = with(binding) {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.buttonShare -> {
                presenter.shareNote(editTitle.text.toString(), editText.text.toString())
            }
            R.id.buttonSave -> {
                presenter.saveNote(editTitle.text.toString(), editText.text.toString())
            }
        }
        return true
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

}

