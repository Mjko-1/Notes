package com.example.notes.ui.dialogs

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.example.notes.R

class LocationAgreePermissionDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it).apply {
                setMessage(R.string.location_agree_dialog_message)
                setNegativeButton(R.string.negative_button_text, null)
                setPositiveButton(R.string.positive_button_text) { _, _ ->
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        101
                    )
                }
            }.create()
        } ?: throw  IllegalStateException("Exception")
    }
}