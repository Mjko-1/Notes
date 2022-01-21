package com.example.notes.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.DialogFragment
import com.example.notes.BuildConfig
import com.example.notes.R

class LocationAgreePermissionDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it).apply {
                setTitle(R.string.location_agree_dialog_title)
                setMessage(R.string.location_agree_dialog_message)
                setNegativeButton(R.string.negative_button_text, null)
                setPositiveButton(R.string.positive_button_text) { _, _ ->
                    startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts(
                                "package",
                                BuildConfig.APPLICATION_ID,
                                null
                            )
                        )
                    )
                }
            }.create()
        } ?: throw  IllegalStateException("Exception")
    }
}