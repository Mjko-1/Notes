package com.example.notes.model

import androidx.lifecycle.LiveData
import com.example.notes.entities.NoteItem

interface NoteRepository {

    fun getNoteLiveDataList(): LiveData<List<NoteItem>>

    suspend fun getNoteList(): List<NoteItem>

    suspend fun getNoteItem(noteItemId: Long): NoteItem

    suspend fun addNote(note: NoteItem)

    suspend fun deleteNote(id: Long)
}