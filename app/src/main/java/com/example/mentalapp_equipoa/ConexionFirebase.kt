package com.example.mentalapp_equipoa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.mentalapp_equipoa.PruebasFirebase.Companion.getMaxId
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
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ConexionFirebase {
    fun insertarTest(usuario:String, sexo:String,fecha:Date, edad:Int, factor1:Int, factor2:Int, factor3:Int): Task<Boolean> {
        //Devuelve una conexion del firebase directa a la base de datos
        val db: FirebaseFirestore = Firebase.firestore

        val exito: TaskCompletionSource<Boolean> = TaskCompletionSource<Boolean>()

        getMaxId("resultados").addOnCompleteListener { res ->
            if (res.result != ((-1).toLong())){
                // Es como un Diccionario de Strings(campos) y cualquier elemento
                val inserto = hashMapOf<String, Any>(
                    "id" to res.result,
                    "usuario" to usuario,
                    "fecha" to fecha,
                    "sexo" to sexo,
                    "edad" to edad,
                    "FACT01" to factor1,
                    "FACT02" to factor2,
                    "FACT03" to factor3
                )

                db.collection("resultados").add(inserto)
                    .addOnSuccessListener { documentReference ->
                        Log.d("prueba", "DocumentSnapshot added with ID: ${documentReference.id}")
                        exito.setResult(true)
                    }
                    .addOnFailureListener { e ->
                        Log.w("prueba", "Error adding document: "+e.message)
                        exito.setResult(false)
                    }
                    .addOnCanceledListener {
                        exito.setResult(false)
                    }
            } else {
                exito.setResult(false)
            }
        }
        return exito.task
    }
    fun getMaxId(coleccion: String): Task<Long> {
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

        loc.addOnFailureListener { e ->
            Log.w("prueba", "Error adding document", e)
            max.setResult(-1)
        }
        //Log.d("test", "Máximo ID fuera : ${max.task.result}")
        return max.task
    }
    fun sincronizarLocalFirebase(context: Context) {
        val bh = DBHelper(context)
        val dbR: SQLiteDatabase = bh.readableDatabase

        // nick es referenica a usuario
        val c = dbR.rawQuery(
            "SELECT resultados.id, resultados.username, gender, age, fecha, factor1, factor2, factor3" +
                    " FROM resultados, user" +
                    " WHERE subido = 0 AND username = name", null
        )
        val restirar: TaskCompletionSource<Boolean> = TaskCompletionSource<Boolean>()

        if (c.moveToFirst()) {
            do {
                val intentoID: Int = c.getInt(0)

                val nick: String = c.getString(1)
                val sexo: String = c.getString(2)
                val edad:Int = c.getInt(3)


                val fechaString: String = c.getString(4)
                val fecha: Date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(fechaString)!!

                val fac1: Int = c.getInt(5)
                val fac2: Int = c.getInt(6)
                val fac3: Int = c.getInt(7)

                insertarTest(nick, sexo, fecha, edad, fac1, fac2, fac3).addOnCompleteListener{ res ->
                    if (res.result){
                        // ** Actualizar la tabla local **
                        val db: SQLiteDatabase = bh.writableDatabase
                        db.beginTransaction()

                        val cvalue = ContentValues()
                        cvalue.put("subido", 1)

                        // Especifica la condición para la actualización (en este ejemplo, basado en el Id)
                        val whereClause = "id = ?"
                        val whereArgs = Array<String>(1) { intentoID.toString() }

                        // Realiza la actualización
                        db.update("resultados", cvalue, whereClause, whereArgs)

                        db.setTransactionSuccessful()
                        db.endTransaction()
                        db.close()
                    }
                }
            } while (c.moveToNext())
            restirar.setResult(true)
        }

        restirar.task.addOnCompleteListener{ ern ->
            c.close()
            dbR.close()
            bh.close()
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