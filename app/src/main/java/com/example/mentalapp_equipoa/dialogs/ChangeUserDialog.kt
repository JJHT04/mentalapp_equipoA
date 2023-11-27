package com.example.mentalapp_equipoa.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.R
import com.example.mentalapp_equipoa.userAge
import com.example.mentalapp_equipoa.userGender
import com.example.mentalapp_equipoa.userName
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ChangeUserDialog: DialogFragment() {

    private var valid: Boolean = false

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater
            builder.setTitle(getString(R.string.enter_the_new_username))
                .setView(inflater.inflate(R.layout.test_dialog_layout, null))
                builder.setNegativeButton("Cancel") {
                        _,_ -> Toast.makeText(activity, "User name change cancelled", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Accept") {
                    _,_ ->
                    val username = requireDialog().findViewById<TextView>(R.id.username).text.toString()
                    val age = requireDialog().findViewById<TextView>(R.id.Age).text.toString()
                    val gender = requireDialog().findViewById<TextView>(R.id.Gender).text.toString()
                    if (username.isNotBlank() && age.isNotBlank() && gender.isNotBlank()){
                        if (gender.lowercase() == "hombre" || gender.lowercase() == "mujer" || gender.lowercase() == "no binario"){
                            userName = username
                            userAge = age
                            userGender = gender
                            Toast.makeText(activity, "User name changed successfully", Toast.LENGTH_SHORT).show()
                            valid = true
                        } else {
                            val toast = Toast.makeText(activity, "Error: you have to enter a valid gender: Male, Female, Non-Binary", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0,0)
                            toast.show()
                        }
                    } else {
                        val toast = Toast.makeText(activity, "Error: empty username, age or gender", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0,0)
                        toast.show()
                    }
                }

            builder.create()

        }?: throw IllegalStateException("Activity can't be null")
    }

}