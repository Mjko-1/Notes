package com.example.notes

import java.io.Serializable

data class NoteItem(
    var title: String,
    var text: String,
    val dateOfCreation: String
) : Serializable
