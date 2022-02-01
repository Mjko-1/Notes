package com.example.notes.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.entities.NoteItem

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table")
    fun getNoteLiveDataList(): LiveData<List<NoteItem>>

    @Query("SELECT * FROM notes_table")
    suspend fun getNoteList(): List<NoteItem>

    @Query("SELECT * FROM notes_table WHERE id=(:id)")
    suspend fun getNote(id: Long): NoteItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteItem)

    @Query("DELETE FROM notes_table WHERE id=(:id)")
    suspend fun deleteNote(id: Long)
}