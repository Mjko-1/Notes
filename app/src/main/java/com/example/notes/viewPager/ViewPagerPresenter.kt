package com.example.notes.viewPager

import com.example.notes.NoteItem
import com.example.notes.model.NoteDatabase

class ViewPagerPresenter(var view: NotesPager.View, private val db: NoteDatabase) :
    NotesPager.Presenter {

    override fun getNoteData(): List<NoteItem> = db.noteDao().getAll()

}