package com.example.notes.model.retrofit

import com.example.notes.entities.NoteItem
import retrofit2.http.GET
import retrofit2.http.Query

interface NoteApi {

    @GET(GET_REQUEST)
    suspend fun getNote(
        @Query("alt") alt: String,
        @Query("token") token: String
    ): NoteItem

    companion object {
        private const val GET_REQUEST = "note.json"
    }
}