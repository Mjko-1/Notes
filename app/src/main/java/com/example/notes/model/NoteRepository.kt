package com.example.notes.model

import androidx.lifecycle.LiveData
import com.example.notes.entities.NoteItem

interface NoteRepository {

    fun getNoteLiveDataList(): LiveData<List<NoteItem>>

    fun getNoteList(): List<NoteItem>

    fun getNoteItem(noteItemId: Long): NoteItem

    fun addNote(note: NoteItem)

    fun deleteNote(id: Long)
}