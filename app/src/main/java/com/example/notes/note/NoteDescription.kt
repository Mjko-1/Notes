package com.example.notes.note

interface NoteDescription {

    interface View {
        fun showMessageEmpty()
        fun showMessage(massage: String)
        fun shareText(noteText: String)
        fun showDialog()
    }

}