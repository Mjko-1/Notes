package com.example.notes.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.model.NetRepository
import com.example.notes.model.NoteRepository

class MainViewModelFactory(
    private val repository: NoteRepository,
    private val networkRepository: NetRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository, networkRepository) as T
    }
}