package com.example.notes.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.entities.NoteItem
import com.example.notes.model.retrofit.NoteApi

class NetRepository(private val noteApi: NoteApi) {

    private val _noteFromNetwork = MutableLiveData<NoteItem>()
    val noteFromNetwork: LiveData<NoteItem>
        get() = _noteFromNetwork

    suspend fun download(): Boolean {
        return try {
            val listCall: NoteItem = noteApi.getNote(alt = ALT, token = TOKEN)
            _noteFromNetwork.value = listCall
            true
        } catch (e: Exception) {
            Log.e("Error of download", e.message.toString())
            false
        }
    }

    companion object {
        private const val ALT = "media"
        private const val TOKEN = "2e000bbb-bc3d-4bb8-8429-fabef81a3b85"
    }
}