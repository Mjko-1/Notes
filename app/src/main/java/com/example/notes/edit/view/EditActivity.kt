package com.example.notes.edit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notes.R
import com.example.notes.databinding.ActivityEditBinding
import com.example.notes.edit.EditNote
import com.example.notes.edit.presenter.EditPresenter

class EditActivity : AppCompatActivity(), EditNote.View {

    private lateinit var binding: ActivityEditBinding

    private lateinit var saveButton: Button
    private lateinit var title: EditText
    private lateinit var text: EditText

    private lateinit var presenter : EditNote.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saveButton = binding.buttonSave
        text = binding.editText
        title = binding.editTitle
        presenter = EditPresenter(this)


        saveButton.setOnClickListener {
            presenter.saveNote(title.text.toString(),text.text.toString())
        }
    }

    override fun showMessage(massage: String) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show()
    }

    override fun showMassageEmpty() {
        Toast.makeText(this, R.string.empty_title_massage, Toast.LENGTH_SHORT).show()
    }

}

