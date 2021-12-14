package com.example.notes.model

import androidx.room.*
import com.example.notes.NoteItem

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table")
    fun getAll(): List<NoteItem>

    @Query("SELECT * FROM notes_table WHERE id=(:id)")
    fun getNote(id: Long): NoteItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: NoteItem)

    @Update
    fun updateNote(note: NoteItem)

    @Query("DELETE FROM notes_table WHERE id=(:id)")
    fun deleteNote(id: Long)
}