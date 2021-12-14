package com.example.notes.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.NoteItem

@Database(entities = [NoteItem::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NotesDao

    companion object {
        private var INSTANCE: NoteDatabase? = null
        private val LOCK = Any()

        private const val DB_NAME = "note_item.db"

        fun getInstance(context: Context): NoteDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
            }
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                DB_NAME
            ).allowMainThreadQueries().build()
            INSTANCE = instance
            return instance
        }
    }
}