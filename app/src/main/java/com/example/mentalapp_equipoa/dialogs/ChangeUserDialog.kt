package com.example.mentalapp_equipoa.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.R
import com.example.mentalapp_equipoa.userName
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ChangeUserDialog: DialogFragment() {

    private var valid: Boolean = false

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater
            builder.setTitle("Enter the new user name")
                .setView(inflater.inflate(R.layout.test_dialog_layout, null))
                builder.setNegativeButton("Cancel") {
                        _,_ -> Toast.makeText(activity, "User name change cancelled", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Accept") {
                    _,_ ->
                    val text = requireDialog().findViewById<TextView>(R.id.Age).text.toString()
                    if (text.isNotBlank()) {
                        userName = text
                        Toast.makeText(activity, "User name changed successfully", Toast.LENGTH_SHORT).show()
                        valid = true
                    } else {
                        val toast = Toast.makeText(activity, "Error: empty username", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0,0)
                        toast.show()
                    }
                }

            builder.create()

        }?: throw IllegalStateException("Activity can't be null")
    }

}