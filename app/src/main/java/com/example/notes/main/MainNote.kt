package com.example.notes.main

import com.example.notes.NoteItem

interface MainNote {

    interface View {}

    interface Presenter {
        fun getNoteData(): List<NoteItem>
    }
}