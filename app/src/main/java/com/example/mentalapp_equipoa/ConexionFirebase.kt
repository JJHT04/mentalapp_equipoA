package com.example.mentalapp_equipoa

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ConexionFirebase {

    fun insertarTest(usuario: Int, fecha: Date, factor1: Int, factor2: Int, factor3: Int) {
        //Devuelve una conexion del firebase directa a la base de datos
        val db: FirebaseFirestore = Firebase.firestore

        getMaxId("resultados2").addOnCompleteListener { res ->
            // Es como un Diccionario de Strings(campos) y cualquier elemento
            val inserto = hashMapOf<String, Any>(
                "id" to res.result,
                "usuario" to usuario,
                "fecha" to fecha,
                "FACT01" to factor1,
                "FACT02" to factor2,
                "FACT03" to factor3
            )

            db.collection("resultados2").add(inserto)
            /*    .addOnSuccessListener { documentReference ->
                    Log.d("prueba", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("prueba", "Error adding document", e)
                }*/
        }
    }

    fun insertarTest(usuario: Int, factor1: Int, factor2: Int, factor3: Int) {
        // Toast.makeText(actividad,"Hola don Pepito",Toast.LENGTH_LONG).show()
        val time: Date = Calendar.getInstance().time

        //Devuelve una conexion del firebase directa a la base de datos
        val db: FirebaseFirestore = Firebase.firestore

        getMaxId("resultados2").addOnCompleteListener { res ->
            // Es como un Diccionario de Strings(campos) y cualquier elemento
            val inserto = hashMapOf<String, Any>(
                "id" to res.result,
                "usuario" to usuario,
                "fecha" to time,
                "FACT01" to factor1,
                "FACT02" to factor2,
                "FACT03" to factor3
            )

            db.collection("resultados2").add(inserto)
            /*    .addOnSuccessListener { documentReference ->
                    Log.d("prueba", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("prueba", "Error adding document", e)
                }*/
        }
    }

    private fun getMaxId(coleccion: String): Task<Long> {
        val db: FirebaseFirestore = Firebase.firestore
        val max: TaskCompletionSource<Long> = TaskCompletionSource<Long>()

        // Realiza la consulta ordenando por el campo 'id' de forma descendente y limitando a 1 resultado
        val loc: Task<QuerySnapshot> = db.collection(coleccion)
            .orderBy("id", Query.Direction.DESCENDING)
            .limit(1)
            .get()

        loc.addOnSuccessListener { documentReference ->
            if (!documentReference.isEmpty) {
                // Obtiene el documento con el valor máximo de id
                val documentoMaxID: DocumentSnapshot = documentReference.documents[0]
                val maxId: Long? = documentoMaxID.getLong("id")
                if (maxId != null) {
                    max.setResult(maxId + 1)
                }
                Log.d("test", "Máximo ID encontrado: ${max.task.result}")
            } else {
                max.setResult(0)
            }
        }
        //Log.d("test", "Máximo ID fuera : ${max.task.result}")
        return max.task
    }

    fun sincronizarLocalFirebase(context: Context) {
        val bh = DBHelper(context)
        val dbR: SQLiteDatabase = bh.readableDatabase
        val db: SQLiteDatabase = bh.writableDatabase

        // nick es referenica a usuario
        val c = dbR.rawQuery(
            "SELECT id, username, fecha, factor1, factor2, factor3, subido" +
                    " FROM resultados" +
                    " WHERE subido = 0", null
        )
        if (c.moveToFirst()) {
            do {
                val intentoID: Int = c.getInt(1)

                val idUsuario: Int = c.getInt(2)

                val fechaString: String = c.getString(3)
                val fecha: Date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(fechaString)!!

                val fac1: Int = c.getInt(4)
                val fac2: Int = c.getInt(5)
                val fac3: Int = c.getInt(6)

                insertarTest(idUsuario, fecha, fac1, fac2, fac3)

                // ** Actualizar la tabla local **
                db.beginTransaction()

                val cvalue = ContentValues()
                cvalue.put("sincronizado", 1)

                // Especifica la condición para la actualización (en este ejemplo, basado en el Id)
                val whereClause = "id = ?"
                val whereArgs = Array<String>(1) { intentoID.toString() }

                // Realiza la actualización
                val filasActualizadas = db.update("resultados", cvalue, whereClause, whereArgs)

                db.setTransactionSuccessful()
                db.endTransaction()
            } while (c.moveToNext())
        } else {
            // Todas Sincronizadas
        }
        c.close()
        db.close()
        dbR.close()
        bh.close()
    }

    fun recuperarTestFirebase() {
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

class TestCon {
    companion object {
        fun hayConexion():Boolean{
            /*val ses: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
            val future: Future<Boolean> = ses.schedule(
                Callable<Boolean> { isOnline() }, // Es como Runnable pero es devuelve lo que indiques
                0,
                TimeUnit.SECONDS
                         return future.get();
            );
            */

            var internet:Boolean = false
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