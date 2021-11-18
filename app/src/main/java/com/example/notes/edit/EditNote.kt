package com.example.notes.edit

interface EditNote {

    interface Model {}

    interface View {

        fun showMessage (massage: String)
        fun showMassageEmpty ()
    }

    interface Presenter {

        fun saveNote (title: String, text: String)

    }
}