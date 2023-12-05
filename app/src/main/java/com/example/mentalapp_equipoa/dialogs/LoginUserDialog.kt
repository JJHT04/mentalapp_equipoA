package com.example.mentalapp_equipoa.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.R
import com.example.mentalapp_equipoa.userAge
import com.example.mentalapp_equipoa.userGender
import com.example.mentalapp_equipoa.userName
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginUserDialog : DialogFragment() {

    private var valid: Boolean = false

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.layout_login, null)

            builder.setTitle(getString(R.string.enter_the_new_username))
                .setView(dialogView)
                .setNegativeButton("Cancelar acceso") { _, _ ->
                    Toast.makeText(activity, "Acceso de usuario cancelado", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Acceder") { _, _ ->
                    val username = dialogView.findViewById<TextView>(R.id.username).text.toString()

                    if (username.isNotBlank() && username == userName) {
                        Toast.makeText(activity, "Acceso realizado con exito", Toast.LENGTH_SHORT).show()
                        valid = true
                    } else {
                        val toast = Toast.makeText(activity, "Error: Nombre de Usuario es obligatorio", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }

            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }
}
