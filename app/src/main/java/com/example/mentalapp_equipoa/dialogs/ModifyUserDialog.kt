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

class ModifyUserDialog : DialogFragment() {

    private var valid: Boolean = false

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.layout_register_modify, null)
            val spinner = dialogView.findViewById<Spinner>(R.id.spiGeneros)
            val lista = listOf("Seleccione su genero", "Hombre", "Mujer", "No binario")

            val adaptador = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lista)
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adaptador

            builder.setTitle(getString(R.string.enter_the_new_username))
                .setView(dialogView)
                .setNegativeButton("Cancelar modificación") { _, _ ->
                    Toast.makeText(activity, "Modificación de usuario cancelado", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Modificar") { _, _ ->
                    val username = dialogView.findViewById<TextView>(R.id.username).text.toString()
                    val age = dialogView.findViewById<TextView>(R.id.Age).text.toString()
                    val genero = spinner.selectedItem.toString()

                    if (username.isNotBlank() && age.isNotBlank() && genero != "Seleccione su genero") {
                        userName = username
                        userAge = age
                        userGender = genero
                        Toast.makeText(activity, "User name changed successfully", Toast.LENGTH_SHORT).show()
                        valid = true
                    } else {
                        val toast = Toast.makeText(activity, "Error: Nombre de Usuario, Edad o Genero son obligatorios", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }

            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }
}
