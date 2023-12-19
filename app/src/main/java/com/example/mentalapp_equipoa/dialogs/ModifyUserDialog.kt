package com.example.mentalapp_equipoa.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.MainActivity
import com.example.mentalapp_equipoa.PreferencesUtil
import com.example.mentalapp_equipoa.PruebasFirebase
import com.example.mentalapp_equipoa.R
import com.example.mentalapp_equipoa.enums.Gender
import com.example.mentalapp_equipoa.showToast
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
            val genderMap = Gender.getGenderMap(requireContext())
            val lista = Gender.getAllStringRepresentations(requireContext())
            val adaptador = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lista)
            val preferencesUtil = PreferencesUtil(requireContext())
            dialogView.findViewById<EditText>(R.id.Age).setText(preferencesUtil.getAge().toString())
            dialogView.findViewById<EditText>(R.id.username).setText(preferencesUtil.getUsername().toString())
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adaptador
            spinner.setSelection(lista.indexOf(preferencesUtil.getGender()?.toString()))

            builder.setTitle(getString(R.string.modify_title) + ": ${preferencesUtil.getUsername()}")
                .setView(dialogView)
                .setNegativeButton("Cancelar") { _, _ ->
                //Toast.makeText(activity, "Modificación de usuario cancelado", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Modificar") { _, _ ->
                    val username = dialogView.findViewById<TextView>(R.id.username).text.toString()
                    val age = dialogView.findViewById<TextView>(R.id.Age).text.toString()

                    if (username.isNotBlank() && age.isNotBlank() && spinner.selectedItemPosition != Spinner.INVALID_POSITION) {
                        userAge = age.toInt()
                        userGender = genderMap[spinner.selectedItem.toString()]


                        if (userAge!! in 7..113) {
                            preferencesUtil.setAge(userAge!!)
                            preferencesUtil.setGender(userGender!!)
                            userName.value = username
                            preferencesUtil.setUsername(userName.value!!)
                            Toast.makeText(activity, "Modificación realizada correctamente", Toast.LENGTH_SHORT).show()
                            PruebasFirebase.modificarUsuario(actividadMain, username, age.toInt(), userGender.toString())
                            PruebasFirebase.modificarUsuarioFirebase(requireContext(), username, age.toInt(), userGender.toString())
                            valid = true
                        } else {
                            showToast(requireContext(), "Introduce una edad válida")
                        }

                    } else {
                        Toast.makeText(activity, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                    }
                }

            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }
}
