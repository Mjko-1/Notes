package com.example.notes.list

import com.example.notes.model.NoteRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class NoteListViewModelTest {

    private lateinit var repository: NoteRepository
    private lateinit var viewModel: NoteListViewModel

    @Before
    fun setUp() {
        repository = mock(NoteRepository::class.java)
        viewModel = NoteListViewModel(repository)
    }

    @Test
    fun testDeleteNote() {
        viewModel.deleteNoteItem(1)
        Mockito.verify(repository).deleteNote(1)
    }
}

