package com.example.mentalapp_equipoa.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.DBHelper
import com.example.mentalapp_equipoa.MainActivity
import com.example.mentalapp_equipoa.PruebasFirebase
import com.example.mentalapp_equipoa.R
import com.example.mentalapp_equipoa.userAge
import com.example.mentalapp_equipoa.userGender
import com.example.mentalapp_equipoa.userName
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisterUserDialog : DialogFragment() {

    private var valid: Boolean = false

    private lateinit var actividadMain: MainActivity

    //Variable y función necesaria para llamar a registrarUsuario(), ya que necesitamos una actividad como parámetro
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
            val lista = listOf("Seleccione su género", "Masculino", "Femenino", "No binario")

            val adaptador = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lista)
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adaptador

            builder.setTitle(getString(R.string.enter_the_new_username))
                .setView(dialogView)
                .setNegativeButton("Cancelar registro") { _, _ ->
                    //Toast.makeText(activity, "Registro de usuario cancelado", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Registrarse") { _, _ ->
                    val username = dialogView.findViewById<TextView>(R.id.username).text.toString()
                    val age = dialogView.findViewById<TextView>(R.id.Age).text.toString()
                    val genero = spinner.selectedItem.toString()

                    if (username.isNotBlank() && age.isNotBlank() && genero != "Seleccione su género") {
                        userName = username
                        userAge = age.toInt() //Que sea solo entero ya está manejado en el componente
                        userGender = genero

                        if(PruebasFirebase.comprobarSiExisteUsuarioLocal(actividadMain, username)){ //Si ya existe
                            Toast.makeText(activity, "Este usuario ya está registrado en este dispositivo", Toast.LENGTH_SHORT).show()
                        }else{
                            PruebasFirebase.registrarUsuarioFirebase(username, Integer.parseInt(age), genero)
                            PruebasFirebase.registrarUsuarioLocal(actividadMain, username, Integer.parseInt(age), genero)
                            Toast.makeText(activity, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                        }
                        valid = true //¿esto no se usa nunca?
                    } else {
                        val toast = Toast.makeText(activity, "Debes rellenar todos los campos", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }

            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }


}
