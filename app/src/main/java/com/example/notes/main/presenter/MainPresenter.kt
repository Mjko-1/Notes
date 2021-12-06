package com.example.notes.main.presenter

import com.example.notes.main.MainNote
import com.example.notes.model.NoteListModel

class MainPresenter(private val view: MainNote.View) : MainNote.Presenter {

    override fun getNoteData() = NoteListModel.getNoteList()
}