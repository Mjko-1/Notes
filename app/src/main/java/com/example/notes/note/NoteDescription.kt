package com.example.notes.note

interface NoteDescription {

    interface View {
        fun showMessageEmpty()
        fun showMessage(message: String)
        fun shareText(noteText: String)
        fun showDialog()
    }

}