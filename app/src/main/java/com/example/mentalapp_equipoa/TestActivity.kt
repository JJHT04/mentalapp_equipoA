package com.example.mentalapp_equipoa

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.ByteArrayOutputStream
import java.io.IOException

var respuestas=Array<Int?>(20){null}
var factor = Array<Int?>(20){null}
class TestActivity : AppCompatActivity() {
    private var preferencesUtil: PreferencesUtil? = null
    private var preguntas2 = Array<String?>(20){null}
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val toolbar: Toolbar = findViewById(R.id.topAppBarTest)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            navigateUpTo(intent)
        }

        preferencesUtil = PreferencesUtil(this)

        val isFirst = preferencesUtil!!.isFirstRun()
        i = preferencesUtil!!.getNumPage()
        if(i == 15){
            findViewById<Button>(R.id.btnSiguiente).apply {
                text = "Mostrar"
            }
        }
        inicializar(isFirst)
        var j = 0
        while (j<20){
            obtenerPregunta(j)
            j++
        }
        cargarPreguntas()
    }
    private fun leerArchivo(): Array<String> {
        val inputStream = resources.openRawResource(R.raw.preguntas)
        val byteArray = ByteArrayOutputStream()
        try {
            var i = inputStream.read()
            while (i != -1) {
                byteArray.write(i)
                i = inputStream.read()
            }
            inputStream.close()
        } catch (io: IOException) {
            io.printStackTrace()
        }
        return byteArray.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
    }

    private fun inicializar(iniciar: Boolean) {
        // Obtener las preferencias compartidas

        if (iniciar) {
            // Realizar acciones de inicialización o configuración inicial
            // Por ejemplo, crear la base de datos o realizar configuraciones iniciales
            val texto: Array<String> = leerArchivo()

            val bh = DBHelper(this)
            val db: SQLiteDatabase = bh.getWritableDatabase()
            db.beginTransaction()
            for (i in texto.indices) {
                if (texto[i] !== "") {
                    val linea = texto[i].split(";".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                    val cvalue = ContentValues()
                    cvalue.put("Id", linea[0])
                    cvalue.put("Pregunta", linea[1])
                    cvalue.put("factor", linea[2])
                    cvalue.put("valor", linea[3])
                    db.insert("Preguntas", null, cvalue)
                }
            }
            db.setTransactionSuccessful()
            db.endTransaction()
            // Guardar el indicador de que la app ya se ejecutó antes
            preferencesUtil?.setFirstRun(false)
        }

    }

    private fun obtenerPregunta(i: Int) {
        val bh = DBHelper(this)
        var l = i+1
        val dbR: SQLiteDatabase = bh.getReadableDatabase()
        val c = dbR.rawQuery("SELECT Pregunta,factor,valor FROM Preguntas Where Id = $l", null)
        if (c.moveToFirst()) {
            do {
                preguntas2[i] = c.getString(0)
                factor[i] = c.getInt(1)
                respuestas[i] = c.getInt(2)
            } while (c.moveToNext())
        }
        c.close()
        dbR.close()
    }
    private fun cargarPreguntas(){
        for(t in 1..5){
            val textView = findViewById<TextView>(
                resources.getIdentifier(
                    "txvPregunta$t",
                    "id",
                    packageName
                )
            )
            var o = i+t
            val bh = DBHelper(this)
            val dbR: SQLiteDatabase = bh.getReadableDatabase()
            val c = dbR.rawQuery("SELECT valor FROM Preguntas Where Id = $o", null)
            var n = 0
            if (c.moveToFirst()) {
                do {
                   n = c.getInt(0)
                } while (c.moveToNext())
            }
            c.close()
            dbR.close()
            if(n != -1) {
                var m = (o - 1) % 5
                var rdb = ((5 * m) + n) + 1
                val rdb1 = findViewById<RadioButton>(
                    resources.getIdentifier(
                        "rdb$rdb",
                        "id",
                        packageName
                    )
                )
                rdb1.isChecked = true

            }
            textView.text = "-" + (i + t) + ". " + preguntas2[i + t - 1]
        }
        i += 5
    }

    fun comprobarRespuestas(): Boolean {
        var mostrar = true
        for (i in 1..5) {
            val radioGroup = findViewById<RadioGroup>(
                resources.getIdentifier(
                    "radioGroup$i",
                    "id",
                    packageName
                )
            )
            val radioButtonId = radioGroup.checkedRadioButtonId
            if (radioButtonId == -1) {
                mostrar = false
            }
        }
        return mostrar
    }
    fun cargarRespuestas(){
        for(o in 1..25){
            val rdb = findViewById<RadioButton>(
                resources.getIdentifier(
                    "rdb$o",
                    "id",
                    packageName
                )
            )
            if(rdb.isChecked){
                for(m in 0..4){
                    if (((o-1)/5).toInt() == m){
                        respuestas[i+m] = (o-1)%5
                    }
                }
            }
        }
    }
    fun limpiarRespuestas(){
        for(i in 1..5){
            val radioGroup = findViewById<RadioGroup>(
                resources.getIdentifier(
                    "radioGroup$i",
                    "id",
                    packageName
                )
            )
            radioGroup.clearCheck();
        }
    }
    fun subirRespuestas(){
        for (k in 1..5) {
            val radioGroup = findViewById<RadioGroup>(
                resources.getIdentifier(
                    "radioGroup$k",
                    "id",
                    packageName
                )
            )
            val radioButtonId = radioGroup.checkedRadioButtonId
            var num = findViewById<RadioButton>(radioButtonId).text.toString().toInt()
            val texto: Array<String> = leerArchivo()
            val bh = DBHelper(this)
            val db: SQLiteDatabase = bh.getWritableDatabase()
            db.beginTransaction()
            var p = i+k-6

            if (texto[p] !== "") {
                    val linea = texto[p].split(";".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()

                    val cvalue = ContentValues()
                    cvalue.put("Pregunta", linea[1])
                    cvalue.put("factor", linea[2])
                    cvalue.put("valor", num)

                    // Especifica la condición para la actualización (en este ejemplo, basado en el Id)
                    val whereClause = "Id = ?"
                    val whereArgs = arrayOf(linea[0])

                    // Realiza la actualización
                    val filasActualizadas = db.update("Preguntas", cvalue, whereClause, whereArgs)
            }

            db.setTransactionSuccessful()
            db.endTransaction()
        }
    }
    fun btnSiguienteOnClick(view: View) {
        if(comprobarRespuestas()){
            subirRespuestas()
            if (i < preguntas2.size) {
                findViewById<TextView>(R.id.txvAlerta).apply { text = "" }
                cargarRespuestas()
                limpiarRespuestas()
                cargarPreguntas()
                if(i == preguntas2.size){
                    findViewById<Button>(R.id.btnSiguiente).apply { text = "Mostrar Resultados" }
                }
            }else{
                //findViewById<TextView>(R.id.txvAlerta).apply {text = calcularNota() }
                findViewById<TextView>(R.id.txvAlerta).apply {text = "Has completado el test" }
                val texto: Array<String> = leerArchivo()
                val bh = DBHelper(this)
                val db: SQLiteDatabase = bh.getWritableDatabase()
                db.beginTransaction()

                for (cc in texto.indices) {
                    if (texto[cc] !== "") {
                        val linea = texto[cc].split(";".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()

                        val cvalue = ContentValues()
                        cvalue.put("Pregunta", linea[1])
                        cvalue.put("factor", linea[2])
                        cvalue.put("valor", linea[3])

                        // Especifica la condición para la actualización (en este ejemplo, basado en el Id)
                        val whereClause = "Id = ?"
                        val whereArgs = arrayOf(linea[0])

                        // Realiza la actualización
                        val filasActualizadas = db.update("Preguntas", cvalue, whereClause, whereArgs)

                        if (filasActualizadas == 0) {
                            // Si no hay filas actualizadas, puedes manejarlo como un caso especial
                            // o decidir insertar el registro si no existe
                        }
                    }
                }
                db.setTransactionSuccessful()
                db.endTransaction()
                findViewById<Button?>(R.id.btnAnterior).apply {
                    isEnabled = false
                }
                i = 0
            }
        }else {
            findViewById<TextView>(R.id.txvAlerta).apply {text = "Tienes que responder todas las preguntas" }
        }
    }
    fun btnAnteriorOnClick(view: View) {
            if ((i-5) > 0) {
                findViewById<TextView>(R.id.txvAlerta).apply { text = "" }
                limpiarRespuestas()
                i -=10
                cargarPreguntas()
                findViewById<Button>(R.id.btnSiguiente).apply { text = "Siguiente" }
            }else{
                findViewById<TextView>(R.id.txvAlerta).apply {text = "No hay preguntas anteriores" }
            }
    }
    fun asignarVariablesCalcularNota(genero: String, edad: Int, factor: Int): Pair<Double, Double> {
        var x = 0.0
        var y = 0.0

        if(genero=="mujer"){
            if(factor==1){
                if(edad<=14){
                    x=19.2
                    y=30.0
                }
                if(edad<=16){
                    x=26.4
                    y=32.4
                }
                if(edad<=18){
                    x=28.2
                    y=38.4
                }
            }
            if(factor==2){
                if(edad<=14){
                    x=0.0
                    y=1.0
                }
                if(edad<=16){
                    x=0.0
                    y=2.0
                }
                if(edad<=18){
                    x=0.0
                    y=1.0
                }
            }
            if(factor==3){
                if(edad<=14){
                    x=26.0
                    y=34.1
                }
                if(edad<=16){
                    x=28.0
                    y=36.0
                }
                if(edad<=18){
                    x=30.0
                    y=35.0
                }
            }
        }
        if(genero=="hombre"){
            if(factor==1){
                if(edad<=14){
                    x=13.2
                    y=20.4
                }
                if(edad<=16){
                    x=14.4
                    y=21.6
                }
                if(edad<=18){
                    x=14.4
                    y=22.0
                }
            }
            if(factor==2){
                if(edad<=14){
                    x=0.0
                    y=1.0
                }
                if(edad<=16){
                    x=0.0
                    y=2.0
                }
                if(edad<=18){
                    x=0.0
                    y=2.5
                }
            }
            if(factor==3){
                if(edad<=14){
                    x=19.0
                    y=28.0
                }
                if(edad<=16){
                    x=19.0
                    y=26.0
                }
                if(edad<=18){
                    x=21.0
                    y=26.0
                }
            }
        }
        if(genero=="no binario"){
            if(factor==1){
                if(edad<=14){
                    x=16.2
                    y=25.2
                }
                if(edad<=16){
                    x=20.4
                    y=32.4
                }
                if(edad<=18){
                    x=21.3
                    y=30.2
                }
            }
            if(factor==2){
                if(edad<=14){
                    x=0.0
                    y=1.0
                }
                if(edad<=16){
                    x=0.0
                    y=2.0
                }
                if(edad<=18){
                    x=0.0
                    y=1.0
                }
            }
            if(factor==3){
                if(edad<=14){
                    x=22.5
                    y=27.0
                }
                if(edad<=16){
                    x=23.5
                    y=31.0
                }
                if(edad<=18){
                    x=25.5
                    y=30.5
                }
            }
        }
        return Pair(x, y)
    }

    /*
    Esta funcion sera cambiada una vez se introduzca la bbd
     */

    fun calcularNota(): String{
        var genero = userGender.toString().lowercase()
        var edad = userAge?.toInt()
        var sumFactor1 = 0
        var sumFactor2 = 0
        var sumFactor3 = 0

        var x = 0.0
        var y = 0.0

        var j = 0
        for(i in 0..factor.size){
            if(i==1){
                sumFactor1 += respuestas[j]!!

            }
            if(i==2){
                sumFactor2+=respuestas[j]!!
            }
            if(i==3){
                sumFactor3+=respuestas[j]!!
            }
            j++
        }

        var sumFactores =  arrayOf<Int>(sumFactor1, sumFactor2, sumFactor3)
        var nivel = arrayOf<String>("","","")

        var t = 1

        for(i in 1..3){
            val variables = edad?.let { asignarVariablesCalcularNota(genero, it, i) }
            x = variables!!.first
            y = variables!!.second

            // si todos los valores son 0 explota Caused by: java.lang.NullPointerException

            if(sumFactores[t-1]<=x){
                nivel[t-1] = "bajo"
            }
            if(sumFactores[t-1]>x && sumFactores[t-1]<=y){
                nivel[t-1] = "medio"
            }
            if(sumFactores[t-1]>y){
                nivel[t-1] = "alto"
            }
        }
        return "resultado"
    }

    override fun onDestroy() {

        if(i != 0) {
            preferencesUtil?.setNumPage(i-5)
        }else{
            preferencesUtil?.setNumPage(i)
        }

        super.onDestroy()
    }
}