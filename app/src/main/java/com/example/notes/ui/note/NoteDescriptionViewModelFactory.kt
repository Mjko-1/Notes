package com.example.notes.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.model.NoteRepository

class NoteDescriptionViewModelFactory(private val repository: NoteRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteDescriptionViewModel(repository) as T
    }
}