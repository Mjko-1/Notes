package com.example.notes.ui.customViews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import com.example.notes.R

class HtmlParseTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0
) : AppCompatTextView(context, attrs, defaultAttrs) {

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.HtmlParseTextView,
            defaultAttrs,
            0
        ).apply {
            try {
                val shouldParseHtml = getBoolean(
                    R.styleable.HtmlParseTextView_htmlParse,
                    false
                )
                if (shouldParseHtml) {
                    val parsedText = HtmlCompat.fromHtml(text as String, FROM_HTML_MODE_COMPACT)
                    text = parsedText
                }
            } finally {
                recycle()
            }
        }
    }
}