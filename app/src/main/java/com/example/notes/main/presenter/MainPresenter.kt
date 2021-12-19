package com.example.notes.main.presenter

import androidx.fragment.app.Fragment
import com.example.notes.NoteItem
import com.example.notes.main.MainNote
import com.example.notes.model.NoteDatabase

class MainPresenter(var view: MainNote.View?, private val db: NoteDatabase) : MainNote.Presenter {

    override fun getNoteData(): List<NoteItem> = db.noteDao().getAll()

    override fun openFragment(resId: Int, classFragment: Fragment, onStack: Boolean) {
        view?.openFragment(resId, classFragment, onStack)
    }

    override fun openNote(note: NoteItem) {
        view?.openNoteDescription(note)
    }

    override fun openEditScreen() {
        view?.openEditActivity()
    }
}