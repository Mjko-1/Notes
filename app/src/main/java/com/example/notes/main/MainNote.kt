package com.example.notes.main

import androidx.fragment.app.Fragment
import com.example.notes.NoteItem

interface MainNote {

    interface View {
        fun openFragment(resId: Int, classFragment: Fragment, onStack: Boolean)
        fun openNoteDescription(note: NoteItem)
        fun showMessage(massage: String)
        fun showMessageEmpty()
        fun shareText(noteText: String)
 //       fun displayActionBar(condition: Boolean)
        fun openEditActivity()
    }

    interface Presenter {
        fun openFragment(resId: Int, classFragment: Fragment, onStack: Boolean)
        fun getNoteData(): List<NoteItem>
        fun openNote(note: NoteItem)
        fun saveNote(title: String, text: String, note: NoteItem)
        fun shareNote(title: String, text: String)
        fun openEditActivity()
        fun showMessage(massage: String)
    }
}