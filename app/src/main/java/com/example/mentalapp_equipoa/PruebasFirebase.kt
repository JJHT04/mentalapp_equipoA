package com.example.mentalapp_equipoa

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import java.util.Calendar

class PruebasFirebase {
    companion object {

        fun iniciarSesion(context: Context, userName: String){
            //cargarResultadosPrevios(); //TODO: crear PreferencesUtil y llamar a setIDFireBase()
        }

        fun modificarUsuarioFirebase(context: Context, userName: String, userAge: Int, userGender: String){
            val db: FirebaseFirestore = FirebaseFirestore.getInstance()

            val userID = PreferencesUtil(context).getIDFireBase()
            db.collection("user")
                .whereEqualTo("id", userID)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        // Obtener el ID del documento encontrado
                        val idDocumento = document.id

                        // Actualizar el documento con el ID encontrado
                        db.collection("user").document(idDocumento)
                            .update(
                                mapOf(
                                    "userName" to userName,
                                    "userAge" to userAge,
                                    "userGender" to userGender
                                )
                            )
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("a", "Error al realizar la consulta", e)
                }

        }

        fun modificarUsuario(context: MainActivity, newName : String, age : Int, gender : String){
            //TODO: SOLO PARA PRUEBAS el oldName como variable y no como parámetro, CAMBIAR A FUNCIONAL cuando sepamos de donde coger el currentUserName
            val oldName = PreferencesUtil(context).getUsername()

            val dbHelper = DBHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put("name", newName)
                put("age", age)
                put("gender", gender)
            }

            val whereClause = "name = ?"
            val whereArgs = arrayOf(oldName)

            val numRowsAffected = db.update("user", values, whereClause, whereArgs)

            if (numRowsAffected > 0) {
                Log.d("tag", "Usuario modificado exitosamente")
            } else {
                Log.d("tag", "Error al modificar el usuario")
            }
        }


        fun registrarUsuarioLocal(context : MainActivity, userName : String, userAge: Int, userGender: String){
            val dbHelper = DBHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put("name", userName)
                put("age", userAge)
                put("gender", userGender)
            }
            val newRowId = db?.insert("user", null, values)

            if(newRowId != -1L){
                Log.d("tag", "success")
            }else{
                Log.d("tag", "err")
            }
        }

        fun comprobarSiExisteUsuarioLocal(context: MainActivity, userName: String): Boolean{
            val dbHelper = DBHelper(context)
            val db = dbHelper.readableDatabase
            val query = "SELECT * FROM ${"user"} WHERE ${"name"} = ?"
            val cursor = db.rawQuery(query, arrayOf(userName)) //se reemplaza así el '?'

            val existe = cursor.count > 0
            cursor.close()
            db.close()

            return existe
        }

        fun registrarUsuarioFirebase(context: Context, userName : String, userAge: Int, userGender: String){
            val db: FirebaseFirestore = Firebase.firestore

            getMaxId("user").addOnCompleteListener { tarea ->
                if (tarea.isSuccessful) {
                    val nuevoId = tarea.result
                    PreferencesUtil(context).setIDFireBase(nuevoId.toLong())
                    val registro = hashMapOf(
                        "id" to nuevoId,
                        "userName" to userName,
                        "userAge" to userAge,
                        "userGender" to userGender
                    )

                    db.collection("user").add(registro)
                }
            }
        }

        fun getMaxId(coleccion:String):Task<Int> {
            val db: FirebaseFirestore = Firebase.firestore
            val maxIdTask: TaskCompletionSource<Int> = TaskCompletionSource()

            db.collection(coleccion)
                .orderBy("id", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documentReference = task.result?.documents?.firstOrNull()
                        val maxId = documentReference?.getLong("id") ?: 0
                        val nuevoMaxId = maxId + 1
                        maxIdTask.setResult(nuevoMaxId.toInt())
                        Log.d("test", "Máximo ID encontrado: $nuevoMaxId")
                    }
            }

            Log.d("test", "Máximo ID fuera : $maxIdTask.task")

            return maxIdTask.task
        }

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
                "sexo" to "Macho",
                "edad" to 34,
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