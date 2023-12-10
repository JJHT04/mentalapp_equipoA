package com.example.mentalapp_equipoa.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.MainActivity
import com.example.mentalapp_equipoa.PruebasFirebase
import com.example.mentalapp_equipoa.R
import com.example.mentalapp_equipoa.userAge
import com.example.mentalapp_equipoa.userGender
import com.example.mentalapp_equipoa.userName
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ModifyUserDialog : DialogFragment() {

    private var valid: Boolean = false
    private lateinit var actividadMain: MainActivity //Necesaria para pasarla por argumentos a PruebasFirebase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            actividadMain = context
        } else {
            throw ClassCastException("$context debe implementar MainActivity")
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.layout_register_modify, null)
            val spinner = dialogView.findViewById<Spinner>(R.id.spiGeneros)
            val lista = listOf("Seleccione su género", "Hombre", "Mujer", "No binario")

            val adaptador = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lista)
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adaptador

            builder.setTitle(getString(R.string.enter_the_new_username))
                .setView(dialogView)
                .setNegativeButton("Cancelar") { _, _ ->
                //Toast.makeText(activity, "Modificación de usuario cancelado", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Modificar") { _, _ ->
                    val username = dialogView.findViewById<TextView>(R.id.username).text.toString()
                    val age = dialogView.findViewById<TextView>(R.id.Age).text.toString()
                    val gender = spinner.selectedItem.toString()

                    if (username.isNotBlank() && age.isNotBlank() && gender != "Seleccione su género") {
                        userName = username
                        userAge = age.toInt()
                        userGender = gender
                        Toast.makeText(activity, "Modificación realizada correctamente", Toast.LENGTH_SHORT).show()
                        PruebasFirebase.modificarUsuario(actividadMain, username, Integer.parseInt(age), gender)
                        PruebasFirebase.modificarUsuarioFirebase(username, Integer.parseInt(age), gender)
                        valid = true
                    } else {
                        Toast.makeText(activity, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                    }
                }

            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }
}
