package com.example.notes.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notes.conventions.Constant
import com.example.notes.entities.NoteItem
import com.example.notes.model.NetRepository
import com.example.notes.model.NoteRepository
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val repository: NoteRepository,
    private val networkRepository: NetRepository
) : ViewModel() {

    val noteFromFirebase: LiveData<NoteItem> = networkRepository.noteFromNetwork

    fun getNote() {
        networkRepository.download()
    }

    @SuppressLint("SimpleDateFormat")
    fun saveNote(noteModel: NoteItem) {
        val currentDate = SimpleDateFormat(Constant.DATE_FORMAT).format(Date())
        repository.addNote(
            NoteItem(
                title = noteModel.title,
                text = noteModel.text,
                dateOfCreation = currentDate
            )
        )
    }
}