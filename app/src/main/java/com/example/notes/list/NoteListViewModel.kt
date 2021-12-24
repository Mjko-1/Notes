package com.example.notes.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notes.NoteItem
import com.example.notes.model.NoteRepository
import com.example.notes.model.room.NoteRepositoryImpl

class NoteListViewModel(context: Context) : ViewModel() {

    private val repository: NoteRepository = NoteRepositoryImpl(context)

    val noteList: LiveData<List<NoteItem>> = repository.getNoteList()

    fun deleteNoteItem(noteItemId: Long) {
        repository.deleteNote(noteItemId)
    }
}