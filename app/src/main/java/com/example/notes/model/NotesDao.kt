package com.example.notes.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.NoteItem

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table")
    fun getNoteList(): LiveData<List<NoteItem>>

    @Query("SELECT * FROM notes_table WHERE id=(:id)")
    fun getNote(id: Long): NoteItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NoteItem)

    @Query("DELETE FROM notes_table WHERE id=(:id)")
    fun deleteNote(id: Long)
}