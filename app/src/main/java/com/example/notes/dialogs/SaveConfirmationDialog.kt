package com.example.notes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.notes.R
import com.example.notes.note.NoteDescriptionFragment

class SaveConfirmationDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it).apply {
                setMessage(R.string.edit_dialog_massage)
                setNegativeButton(R.string.negative_button_text, null)
                setPositiveButton(R.string.positive_button_text) { _, _ ->
                    setFragmentResult(NoteDescriptionFragment.CONFIRMATION_TAG, Bundle().apply {
                        putBoolean(NoteDescriptionFragment.AGREE_TAG, true)
                    })
                }
            }.create()
        } ?: throw  IllegalStateException("Exception")
    }
}

