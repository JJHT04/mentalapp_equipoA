package com.example.mentalapp_equipoa

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.Calendar
class PruebasFirebase {
    companion object {
        fun insertar(actividad: Activity) {
            // Toast.makeText(actividad,"Hola don Pepito",Toast.LENGTH_LONG).show()
            val time: Calendar = Calendar.getInstance()

            //Devuelve una conexion del firebase directa a la base de datos
            val db: FirebaseFirestore = Firebase.firestore

            // Es como un Diccionario de Strings(campos) y cualquier elemento
            val inserto = hashMapOf<String, Any>(
                "id" to 1,
                "usuario" to "Juse",
                "fecha" to time,
                "FACT01" to 3,
                "FACT02" to 4,
                "FACT03" to 5,
                "FACT04" to 6,
            )

            db.collection("resultados")
                .add(inserto)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(actividad,"Insertado con exito",Toast.LENGTH_LONG).show()
                    Log.d("prueba", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Toast.makeText(actividad,"Error en la insertado",Toast.LENGTH_LONG).show()
                    Log.w("prueba", "Error adding document", e)
                }
        }

        fun recuperar(actividad: Activity) {
            //Devuelve una conexion del firebase directa a la base de datos
            val db: FirebaseFirestore = Firebase.firestore

            db.collection("resultados")
                //.whereEqualTo("capital", true) // campo -> valor buscado
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("Holi", "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Holi", "Error getting documents: ", exception)
                }
        }
    }
}