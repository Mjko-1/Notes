package com.example.notes.note

import com.example.notes.NoteItem

interface NoteDescription {

    interface View{
        fun showMessageEmpty()
        fun showMessage(massage: String)
        fun shareText(noteText: String)
        fun showDialog(title: String, text: String, note: NoteItem)
    }

}