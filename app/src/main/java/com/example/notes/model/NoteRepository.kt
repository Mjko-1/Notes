package com.example.notes.model

import androidx.lifecycle.LiveData
import com.example.notes.NoteItem

interface NoteRepository {

    fun getNoteList(): LiveData<List<NoteItem>>

    fun getNoteItem(noteItemId: Long): NoteItem

    fun addNote(note: NoteItem)

    fun deleteNote(id: Long)
}