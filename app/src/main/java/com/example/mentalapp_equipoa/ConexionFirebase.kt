package com.example.mentalapp_equipoa

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
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
import java.util.Calendar
import java.util.Date

class ConexionFirebase {
    private fun insertar(actividad: Activity) {
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
            "FACT03" to 12
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
    fun insertarTest(usuario:String, sexo:String, fecha:Date, edad:Int, factor1:Int, factor2:Int, factor3:Int) {
        //Devuelve una conexion del firebase directa a la base de datos
        val db: FirebaseFirestore = Firebase.firestore

        getMaxId("resultados2").addOnCompleteListener { res ->
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

            db.collection("resultados2").add(inserto)
            /*    .addOnSuccessListener { documentReference ->
                    Log.d("prueba", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("prueba", "Error adding document", e)
                }*/
        }
    }
    fun insertarTest(usuario:String, sexo:String, edad:Int, factor1:Int, factor2:Int, factor3:Int) {
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
                "sexo" to sexo,
                "edad" to edad,
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
    private fun getMaxId(coleccion:String):Task<Long>{
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
                val documentoMaxID:DocumentSnapshot = documentReference.documents[0]
                val maxId: Long?  = documentoMaxID.getLong("id")
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
    fun sincronizarLocalFirebase(context: Context){
        val bh = DBHelper(context)
        val dbR: SQLiteDatabase = bh.readableDatabase
        val db: SQLiteDatabase = bh.writableDatabase

        // nick es referenica a usuario
        val c = dbR.rawQuery("SELECT intento, nick, factor1, factor2, factor3, sincronizado FROM Respuestas WHERE sincronizado = false", null)
        if (c.moveToFirst()) {
            do {
                val intento:Int = c.getInt(0)
                val nombre:String = c.getString(0)
                val sexo:String = c.getString(0)
                val fecha:Date = Date() // c.getDate(0) ¿No existe Date?
                val edad:Int = c.getInt(0)
                val fac1:Int = c.getInt(0)
                val fac2:Int = c.getInt(0)
                val fac3:Int = c.getInt(0)

                insertarTest(nombre,sexo,fecha,edad,fac1,fac2,fac3)

                // ** Actualizar la tabla local **
                db.beginTransaction()

                val cvalue = ContentValues()
                cvalue.put("sincronizado", true)

                // Especifica la condición para la actualización (en este ejemplo, basado en el Id)
                val whereClause = "intento = ?"
                val whereArgs = Array<String>(1){ intento.toString() }

                // Realiza la actualización
                val filasActualizadas = db.update("Respuestas", cvalue, whereClause, whereArgs)

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