package com.example.notes.viewPager

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.NoteItem
import com.example.notes.model.NoteRepository
import com.example.notes.model.room.NoteRepositoryImpl

class NotesPagerViewModel(context: Context) : ViewModel() {

    private val repository: NoteRepository = NoteRepositoryImpl(context)

    val noteList: LiveData<List<NoteItem>> = repository.getNoteList()

    private val _fragmentId = MutableLiveData<Int>()
    val fragmentId: LiveData<Int>
        get() = _fragmentId

    fun setStartFragmentItemId(noteItemId: Long){
        _fragmentId.value = noteList.value?.indexOf(repository.getNoteItem(noteItemId))
    }

}