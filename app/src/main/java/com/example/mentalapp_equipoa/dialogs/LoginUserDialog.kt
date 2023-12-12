package com.example.mentalapp_equipoa.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.mentalapp_equipoa.MainActivity
import com.example.mentalapp_equipoa.PruebasFirebase
import com.example.mentalapp_equipoa.R
import com.example.mentalapp_equipoa.showToast
import com.example.mentalapp_equipoa.userName
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginUserDialog : DialogFragment() {

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
            val dialogView = inflater.inflate(R.layout.layout_login, null)

            builder.setTitle(getString(R.string.login_title))
                .setView(dialogView)
                .setNegativeButton("Cancelar") { _, _ ->
                    //Toast.makeText(activity, "Acceso de usuario cancelado", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Acceder") { _, _ ->
                    val username = dialogView.findViewById<TextView>(R.id.username).text.toString()

                    if (username.isNotBlank() && username != userName.value) {
                        if(PruebasFirebase.comprobarSiExisteUsuarioLocal(actividadMain, username)){
                            PruebasFirebase.iniciarSesion(requireContext(), username)
                            Toast.makeText(activity, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(activity, "Este usuario no está registrado en este dispositivo", Toast.LENGTH_SHORT).show()
                        }
                        valid = true //??
                    } else if (username.isBlank()) {
                        val toast = Toast.makeText(activity, "Introduce el nombre", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    } else {
                        showToast(requireContext(), "$username ya esta iniciado sesión")
                    }
                }

            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }
}
