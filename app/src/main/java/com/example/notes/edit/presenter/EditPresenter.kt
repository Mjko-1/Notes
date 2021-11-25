package com.example.notes.edit.presenter

import com.example.notes.edit.EditNote

class EditPresenter (private val view: EditNote.View) : EditNote.Presenter {

    override fun saveNote(title: String, text: String) {
        if (text.isEmpty()) view.showMessageEmpty()
        else {
            val titleInMassage = if (title.isEmpty()) "Note" else title
            view.showMessage("$titleInMassage saved")
        }
    }

    override fun shareNote(title: String, text: String) {
        val noteText = if (title.isEmpty()) text else "${title}\n${text}"
        view.shareText(noteText)
    }
}