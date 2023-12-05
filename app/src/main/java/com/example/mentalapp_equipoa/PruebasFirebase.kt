package com.example.mentalapp_equipoa

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.Calendar
class PruebasFirebase {
    companion object {
        fun pruebasMongo() {
            val time: Calendar = Calendar.getInstance()
            val db: FirebaseFirestore = Firebase.firestore

            val inserto = hashMapOf(
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
                    Log.d("prueba", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("prueba", "Error adding document", e)
                }
        }
    }
}