package com.example.notes.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.entities.NoteItem
import com.example.notes.model.NoteRepository

class NoteListViewModel(private val repository: NoteRepository) : ViewModel() {

    val noteList: LiveData<List<NoteItem>> = repository.getNoteLiveDataList()

    private val _displayedNoteList: MutableLiveData<List<NoteItem>> = MutableLiveData()
    val displayedNoteList: LiveData<List<NoteItem>>
        get() = _displayedNoteList

    fun deleteNoteItem(noteItemId: Long) {
        repository.deleteNote(noteItemId)
    }

    fun searchNote(text: String) {
        _displayedNoteList.value = if (text.isNotEmpty()) {
            noteList.value?.filter {
                it.title.contains(text, true) || it.text.contains(text, true)
            }
        } else noteList.value
    }

    fun updateList(list: List<NoteItem>) {
        _displayedNoteList.value = list
    }
}