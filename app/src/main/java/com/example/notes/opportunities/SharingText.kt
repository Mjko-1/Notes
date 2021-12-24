package com.example.notes.opportunities

import android.content.Context
import android.content.Intent

class SharingText {

    fun shareText(text: String, context: Context) {
        context.startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        })
    }
}