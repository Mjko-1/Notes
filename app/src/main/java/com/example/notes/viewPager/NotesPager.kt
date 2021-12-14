package com.example.notes.viewPager

import com.example.notes.NoteItem

interface NotesPager {

    interface View

    interface Presenter {
        fun getNoteData(): List<NoteItem>
    }
}