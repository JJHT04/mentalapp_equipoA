package com.example.mentalapp_equipoa.dialogs

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.EXTRA_MESSAGE
import com.example.mentalapp_equipoa.R
import com.example.mentalapp_equipoa.TestActivity
import com.example.mentalapp_equipoa.userName
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TestDialog(private val title: String, private val message: String, private val iconID: Int): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok) {
                    _,_ ->
                    val intent = Intent(it, TestActivity::class.java).apply {
                        putExtra(EXTRA_MESSAGE, userName)
                    }
                    startActivity(intent)
                }
                .setIcon(iconID)
            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }

}