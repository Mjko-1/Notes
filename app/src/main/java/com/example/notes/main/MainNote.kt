package com.example.notes.main

import androidx.fragment.app.Fragment
import com.example.notes.NoteItem

interface MainNote {

    interface View {
        fun openFragment (resId: Int, classFragment: Fragment, onStack: Boolean)
    }

    interface Presenter {
        fun getNoteData():List<NoteItem>
    }
}