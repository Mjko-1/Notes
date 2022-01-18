package com.example.notes.ui.viewPager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.entities.NoteItem
import com.example.notes.model.NoteRepository
import kotlinx.coroutines.launch

class NotesPagerViewModel(private val repository: NoteRepository) : ViewModel() {

    val noteList: LiveData<List<NoteItem>> = repository.getNoteLiveDataList()

    private val _fragmentId = MutableLiveData<Int>()
    val fragmentId: LiveData<Int>
        get() = _fragmentId

    fun setStartFragmentItemId(noteItemId: Long) {
        viewModelScope.launch {
            _fragmentId.value = noteList.value?.indexOf(repository.getNoteItem(noteItemId))
        }
    }
}