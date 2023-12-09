package com.example.mentalapp_equipoa

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import java.util.Calendar
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class ConexionFirebase {
    companion object {
        fun insertar(actividad: Activity) {
            // Toast.makeText(actividad,"Hola don Pepito",Toast.LENGTH_LONG).show()
            val time: Calendar = Calendar.getInstance()

            //Devuelve una conexion del firebase directa a la base de datos
            val db: FirebaseFirestore = Firebase.firestore

            Toast.makeText(actividad,"Insertado con exito",Toast.LENGTH_LONG).show()

            // Es como un Diccionario de Strings(campos) y cualquier elemento
            val inserto = hashMapOf<String, Any>(
                "id" to 1,
                "usuario" to "Alvaro",
                "fecha" to time,
                "sexo" to "Macho",
                "edad" to 18,
                "FACT01" to 18,
                "FACT02" to 21,
                "FACT03" to 12,
                "FACT04" to 32,
            )

            db.collection("resultados2")
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

        fun vaciarCollection(actividad: Activity) {
            val db: FirebaseFirestore = Firebase.firestore
            TODO("No va primo")
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

        fun recuperarLocal(actividad: Activity) {
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

        private fun obtenerPregunta(actividad: Activity, i: Int,) {
            val bh:DBHelper = DBHelper(actividad)

            val dbR: SQLiteDatabase = bh.readableDatabase

            val c = dbR.rawQuery("SELECT Pregunta,factor FROM Preguntas WHERE Id = $i", null)
            if (c.moveToFirst()) {
                do {
                    val pregunta = c.getString(0)
                    val factor = c.getInt(1)
                } while (c.moveToNext())
            }
            c.close()
            dbR.close()
        }

        fun hayConexion():Boolean{
            /*val ses: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
            val future: Future<Boolean> = ses.schedule(
                Callable<Boolean> { isOnline() }, // Es como Runnable pero es devuelve lo que indiques
                0,
                TimeUnit.SECONDS
                         return future.get();
            );
            */

            var internet:Boolean = false;
            val thread = Thread { internet = isOnline() }
            thread.start()
            thread.join()
            return internet
        }
        // TCP/HTTP/DNS (depending on the port, 53=DNS, 80=HTTP, etc.)
        private fun isOnline(): Boolean {
            return try {
                val timeoutMs = 2000
                val sock = Socket()
                val sockaddr: SocketAddress = InetSocketAddress("8.8.8.8", 53)
                sock.connect(sockaddr, timeoutMs)
                sock.close()
                true
            } catch (e: IOException) {
                false
            }
        }
    }
}