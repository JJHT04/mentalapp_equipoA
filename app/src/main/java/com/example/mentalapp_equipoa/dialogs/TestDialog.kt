package com.example.mentalapp_equipoa.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.R
import com.example.mentalapp_equipoa.previous_results
import com.google.android.material.dialog.MaterialAlertDialogBuilder

var resultNumber = 1

class TestDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            builder.setTitle(R.string.How_are_you)
                .setItems(R.array.test_items) {
                    _, which ->
                    val items = resources.getStringArray(R.array.test_items)
                    val text = "You feel ${items[which]}"
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                    previous_results.add("Result ${resultNumber++}: $text")
                }
            builder.setIcon(R.drawable.baseline_edit_24)
                .setNegativeButton("Cancel") {
                    _,_ ->
                    Toast.makeText(activity, "Test cancelled", Toast.LENGTH_SHORT).show()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cant be null")
    }
}