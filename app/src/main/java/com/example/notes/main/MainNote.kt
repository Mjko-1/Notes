package com.example.notes.main

import androidx.fragment.app.Fragment
import com.example.notes.NoteItem

interface MainNote {

    interface View {
        fun openFragment(resId: Int, classFragment: Fragment, onStack: Boolean)
        fun openNoteDescription(note: NoteItem)
        fun openEditActivity()
    }

    interface Presenter {
        fun openFragment(resId: Int, classFragment: Fragment, onStack: Boolean)
        fun getNoteData(): List<NoteItem>
        fun openNote(note: NoteItem)
        fun openEditScreen()
    }
}