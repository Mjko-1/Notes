package com.example.notes.viewPager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.model.NoteRepository

class NotesPagerViewModelFactory(private val repository: NoteRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesPagerViewModel(repository) as T
    }
}