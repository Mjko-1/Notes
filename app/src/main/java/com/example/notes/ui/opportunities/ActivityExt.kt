package com.example.notes.ui.opportunities

import android.content.Context
import android.content.Intent

fun Context.shareText(text: String) {
    this.startActivity(Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    })
}
