package com.example.notes.main.presenter

import androidx.fragment.app.Fragment
import com.example.notes.NoteItem
import com.example.notes.main.MainNote
import com.example.notes.model.NoteListRepository

class MainPresenter(var view: MainNote.View?) : MainNote.Presenter {

    override fun openFragment(resId: Int, classFragment: Fragment, onStack: Boolean) {
        view?.openFragment(resId, classFragment, onStack)
    }

    override fun getNoteData(): List<NoteItem> = NoteListRepository.getNoteList()

    override fun openNote(note: NoteItem) {
        view?.openNoteDescription(note)
    }

    override fun saveNote(title: String, text: String, note: NoteItem) {
        if (text.isEmpty()) view?.showMessageEmpty()
        else {
            note.title = title
            note.text = text
            val titleInMassage = if (title.isEmpty()) "Note" else title
            view?.showMessage("$titleInMassage saved")
        }
    }

    override fun shareNote(title: String, text: String) {
        if (title.isEmpty() || text.isEmpty()) view?.showMessage("Note is empty")
        else {
            val noteText = if (title.isEmpty()) text else "${title}\n${text}"
            view?.shareText(noteText)
        }
    }

    override fun openEditActivity() {
        view?.openEditActivity()
    }
}