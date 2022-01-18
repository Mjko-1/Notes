package com.example.notes.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.conventions.Constant
import com.example.notes.entities.NoteItem
import com.example.notes.model.NetRepository
import com.example.notes.model.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val repository: NoteRepository,
    private val networkRepository: NetRepository
) : ViewModel() {

    val noteFromFirebase: LiveData<NoteItem> = networkRepository.noteFromNetwork

    fun getNote() {
        viewModelScope.launch{
            networkRepository.download()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun saveNote(noteModel: NoteItem) {
        viewModelScope.launch {
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
}