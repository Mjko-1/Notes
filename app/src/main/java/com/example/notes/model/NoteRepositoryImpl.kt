package com.example.notes.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.notes.entities.NoteItem
import com.example.notes.model.room.NoteDatabase

class NoteRepositoryImpl(context: Context) : NoteRepository {

    private val noteDB = NoteDatabase.getInstance(context)

    override fun getNoteLiveDataList(): LiveData<List<NoteItem>> =
        noteDB.noteDao().getNoteLiveDataList()

    override fun getNoteList() = noteDB.noteDao().getNoteList()

    override fun getNoteItem(noteItemId: Long): NoteItem {
        return noteDB.noteDao().getNote(noteItemId)
    }

    override fun addNote(note: NoteItem) {
        noteDB.noteDao().insertNote(note)
    }

    override fun deleteNote(id: Long) {
        noteDB.noteDao().deleteNote(id)
    }
}