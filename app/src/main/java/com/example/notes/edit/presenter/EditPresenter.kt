package com.example.notes.edit.presenter

import android.annotation.SuppressLint
import com.example.notes.Constant
import com.example.notes.NoteItem
import com.example.notes.edit.EditNote
import com.example.notes.model.NoteListModel
import java.text.SimpleDateFormat
import java.util.*

class EditPresenter(private val view: EditNote.View) : EditNote.Presenter {

    @SuppressLint("SimpleDateFormat")
    override fun saveNote(title: String, text: String) {
        if (text.isEmpty()) view.showMessageEmpty()
        else {
            val currentDate = SimpleDateFormat(Constant.DATE_FORMAT).format(Date())

            NoteListModel.addNoteToList(NoteItem(title, text, currentDate))
            val titleInMassage = if (title.isEmpty()) "Note" else title
            view.showMessage("$titleInMassage saved")
        }
    }

    override fun shareNote(title: String, text: String) {
        if (title.isEmpty() || text.isEmpty()) view.showMessage("Note is empty")
        else {
            val noteText = if (title.isEmpty()) text else "${title}\n${text}"
            view.shareText(noteText)
        }
    }
}