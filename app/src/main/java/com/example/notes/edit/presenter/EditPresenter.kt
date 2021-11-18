package com.example.notes.edit.presenter

import com.example.notes.edit.EditNote

class EditPresenter (private val view: EditNote.View) : EditNote.Presenter {

    override fun saveNote(title: String, text: String) {

        if (title.isEmpty()) view.showMassageEmpty()
        else view.showMessage("$title saved")

    }

}