package com.example.notes.main.presenter

import com.example.notes.NoteItem
import com.example.notes.main.MainNote
import com.example.notes.model.NoteListRepository

class MainPresenter(private val view: MainNote.View?) : MainNote.Presenter {

    override fun getNoteData(): List<NoteItem> = NoteListRepository.getNoteList()
}