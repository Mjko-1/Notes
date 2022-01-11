package com.example.notes.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.entities.NoteItem
import com.example.notes.model.retrofit.NoteApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetRepository(private val noteApi: NoteApi) {

    private val _noteFromNetwork = MutableLiveData<NoteItem>()
    val noteFromNetwork: LiveData<NoteItem>
        get() = _noteFromNetwork

    fun download() {
        val listCall: Call<NoteItem> = noteApi.getNote(alt = ALT, token = TOKEN)

        listCall.enqueue(object : Callback<NoteItem> {
            override fun onResponse(call: Call<NoteItem>, response: Response<NoteItem>) {
                _noteFromNetwork.value = response.body()
            }

            override fun onFailure(call: Call<NoteItem>, t: Throwable) {
                Log.e("Error of download", t.message.toString())
            }
        })
    }

    companion object {
        private const val ALT = "media"
        private const val TOKEN = "2e000bbb-bc3d-4bb8-8429-fabef81a3b85"
    }
}