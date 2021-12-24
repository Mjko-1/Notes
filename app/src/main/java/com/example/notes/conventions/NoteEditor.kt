package com.example.notes.conventions

interface NoteEditor {

    var currentFragment: ActionWithNoteFragment?

    fun shareText() {
        currentFragment?.tryToShareText()
    }

    fun saveNote() {
        currentFragment?.tryToSaveText()
    }
}