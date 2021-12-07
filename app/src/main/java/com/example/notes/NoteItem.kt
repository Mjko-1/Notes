package com.example.notes

import java.io.Serializable

data class NoteItem(
    var title: String,
    var text: String,
    val dateOfCreation: String,
    var id: Long = UNDEFINED_ID
) : Serializable {

    companion object {
        const val UNDEFINED_ID: Long = -1
    }
}


