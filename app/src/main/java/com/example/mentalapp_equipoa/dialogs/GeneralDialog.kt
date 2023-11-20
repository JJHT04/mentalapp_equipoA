package com.example.mentalapp_equipoa.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GeneralDialog(private val title: String, private val message: String, private val iconID: Int): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok) {
                    _,_ -> //No action needed for now
                }
                .setIcon(iconID)
            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }

}