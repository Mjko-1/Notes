package com.example.notes.edit

interface EditNote {

    interface View {
        fun showMessage(massage: String)
        fun showMessageEmpty()
        fun shareText(noteText: String)
    }

    interface Presenter {
        fun saveNote(title: String, text: String)
        fun shareNote(title: String, text: String)
    }
}