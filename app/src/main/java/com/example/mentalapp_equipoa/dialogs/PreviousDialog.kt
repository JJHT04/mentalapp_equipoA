package com.example.mentalapp_equipoa.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.R
import com.example.mentalapp_equipoa.previous_results
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PreviousDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val icon = if (previous_results.isNotEmpty()) R.drawable.baseline_recent_actors_24 else R.drawable.baseline_error_24
            val title = if (previous_results.isNotEmpty()) "Previous Results" else "Warning"
            val text = if (previous_results.isEmpty()) "There are not any previous result" else {
                var result = ""
                previous_results.forEach { str ->
                    result += str + "\n"
                }

                result
            }
            builder.setIcon(icon)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton("OK") {
                    _,_ ->
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cant be null")
    }
}