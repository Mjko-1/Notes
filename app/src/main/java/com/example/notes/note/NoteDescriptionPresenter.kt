package com.example.notes.note

import com.example.notes.NoteItem
import com.example.notes.model.NoteDatabase

class NoteDescriptionPresenter(var view: NoteDescription.View?, private val db: NoteDatabase) {

    fun tryToSaveNote(title: String, text: String, note: NoteItem) {
        if (title == note.title && text == note.text) showMessage("Note unchanged")
        else view?.showDialog(title, text, note)
    }

    fun saveNote(title: String, text: String, note: NoteItem) {
        if (text.isEmpty()) view?.showMessageEmpty()
        else {
            note.title = title
            note.text = text
            db.noteDao().updateNote(note)
            val titleInMassage = if (title.isEmpty()) "Note" else title
            view?.showMessage("$titleInMassage saved")
        }
    }

    fun shareNote(title: String, text: String) {
        if (title.isEmpty() || text.isEmpty()) view?.showMessage("Note is empty")
        else {
            val noteText = if (title.isEmpty()) text else "${title}\n${text}"
            view?.shareText(noteText)
        }
    }

    private fun showMessage(message: String) {
        view?.showMessage(message)
    }
}