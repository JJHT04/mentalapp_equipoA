package com.example.mentalapp_equipoa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar
import com.example.mentalapp_equipoa.enums.Gender
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

var respuestas=Array<Int?>(20){null}
var factor = Array<Int?>(20){null}

fun getIconHappy (context: Context): Drawable? {
    return when (userGender) {
        Gender.FEMALE -> AppCompatResources.getDrawable(context, R.drawable.female_icon_happy)

        else -> AppCompatResources.getDrawable(context, R.drawable.non_binary_icon_happy)
    }
}

fun getIconAnnoyed (context: Context): Drawable? {
    return when (userGender) {
        Gender.FEMALE -> AppCompatResources.getDrawable(context, R.drawable.female_icon_annoyed)

        else -> AppCompatResources.getDrawable(context, R.drawable.non_binary_icon_annoyed)
    }
}

class TestActivity : AppCompatActivity() {
    private var progressBar: IconRoundCornerProgressBar? = null
    private lateinit var preferencesUtil: PreferencesUtil
    private var preguntas2 = Array<String?>(20){null}
    private var i: Int = 0
    private var iconChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val toolbar: Toolbar = findViewById(R.id.topAppBarTest)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            navigateUpTo(intent)
        }

        progressBar = findViewById(R.id.iconRoundCornerProgressBar)

        preferencesUtil = PreferencesUtil(this)

        val isFirst = preferencesUtil!!.isFirstRun()
        i = preferencesUtil!!.getNumPage()

        progressBar?.apply {
            setProgress(i)
            setIconImageDrawable(getIconHappy(this@TestActivity))
        }

        if(i == 15){
            findViewById<Button>(R.id.btnSiguiente).apply {
                text = getString(R.string.mostrar)
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

    override fun onNavigateUp(): Boolean {
        numPag.value = preferencesUtil.getNumPage()
        return super.onNavigateUp()
    }

    override fun onDestroy() {
        numPag.value = preferencesUtil.getNumPage()
        super.onDestroy()
    }

    override fun onStop() {
        numPag.value = preferencesUtil.getNumPage()
        super.onStop()
    }

    override fun onPause() {
        numPag.value = preferencesUtil.getNumPage()
        super.onPause()
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
                cargarRespuestas()
                limpiarRespuestas()
                cargarPreguntas()
                if(i == preguntas2.size){
                    findViewById<Button>(R.id.btnSiguiente).apply { text = getString(R.string.mostrar) }
                }

                progressBar?.apply {
                    setProgress(i-5)
                    if (iconChange) {
                        setIconImageDrawable(getIconHappy(this@TestActivity))
                        iconChange = false
                    }
                }
            }else{
                findViewById<TextView>(R.id.txvAlerta).apply {text = calcularNota() }
                //findViewById<TextView>(R.id.txvAlerta).apply {text = "Has completado el test" }

                var sincronizado:Boolean = true
                // ** Firebase **
                val factores:Array<Int> = calcularFactores()

                if (TestCon.hayConexion()){
                    Log.i("aus","Si hay conexion")
                    preferencesUtil = PreferencesUtil(this)
                    val con:ConexionFirebase = ConexionFirebase()

                    val usuario:String? = preferencesUtil.getUsername()
                    val sexo: String = preferencesUtil.getGender().toString()
                    val edad: Int = preferencesUtil.getAge()

                    Log.i("aus","Usuario -> ${usuario}")

                    val trio:Triple<Int,Int,Int> = Triple(factores[0],factores[1],factores[2])

                    /*val bh = DBHelper(this)
                    val dbR: SQLiteDatabase = bh.readableDatabase

                    val c = dbR.rawQuery("SELECT id FROM user WHERE name = $usuario ",null)
                    var idUsuario:Int = -1

                    if (c.moveToFirst()) {
                        idUsuario = c.getInt(0)
                    }

                    c.close()
                    dbR.close()
                    bh.close()*/

                    Log.i("aus","Factor 1 -> ${trio.first}. Factor 2 -> ${trio.second}. Factor 3 -> ${trio.third}.")
                    if (usuario != null) {
                        con.insertarTest(usuario,sexo, edad,trio.first,trio.second,trio.third)
                    } else {
                        val usuario:String? = userName.value
                        val sexo:String? = userGender?.name
                        val edad:Int? = userAge
                        if (sexo != null && usuario != null && edad != null) {
                            con.insertarTest(usuario,sexo,edad,trio.first,trio.second,trio.third)
                        } else {
                            sincronizado = false
                        }
                    }
                } else {
                    sincronizado = false
                    Log.i("aus","No hay conexion")
                }

                guardarResultados(factores,sincronizado)


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
                findViewById<Button?>(R.id.btnSiguiente).apply {
                    isEnabled = false
                }
                progressBar?.setProgress(20)
                i = 0

            }
            destruction()
        }else {
            showToast(this, "Contesta todas las preguntas")
        }
    }
    fun btnAnteriorOnClick(view: View) {
            if ((i-5) > 0) {
                findViewById<TextView>(R.id.txvAlerta).apply { text = "" }
                limpiarRespuestas()
                i -=10
                cargarPreguntas()
                findViewById<Button>(R.id.btnSiguiente).apply { text = "Siguiente" }
                progressBar?.apply {
                    setProgress(i-5)

                    if (!iconChange) {
                        setIconImageDrawable(getIconAnnoyed(this@TestActivity))
                        iconChange = true
                    }
                }
            }else{
                showToast(this, "No hay preguntas anteriores")
            }
    }
    fun asignarVariablesCalcularNota(factor: Int): Pair<Int, Int> {
        var x = 0
        var y = 0
        if (factor == 1) {
            x = 15
            y = 23
        }
        if (factor == 2) {
            x = 15
            y = 21
        }
        if (factor == 3) {
            x = 2
            y = 3
        }
        return Pair(x, y)
    }

    fun asignarVariables2(factor: Int): Pair<Int, Int> {
        var x = 0
        var y = 0
        if (factor == 0) {
            x = 31
            y = 50
        }
        if (factor == 1) {
            x = 29
            y = 46
        }
        if (factor == 2) {
            x = 16
            y = 27
        }
        if (factor == 3) {
            x = 16
            y = 29
        }
        return Pair(x, y)
    }

    fun calcularFactores(): Array<Int>{
        val sumFactores = arrayOf<Int>(0,0,0)
        val bh = DBHelper(this)
        val dbR: SQLiteDatabase = bh.readableDatabase

        for(i in 1..3){
            val c = dbR.rawQuery("SELECT SUM(valor) FROM Preguntas Where factor = $i", null)
            if(c.moveToFirst()) {
                do {
                    sumFactores[i-1] = c.getInt(0)
                } while (c.moveToNext())
            }
            c.close()
        }

        dbR.close()

        return sumFactores
    }

    fun calcularNota(): String{
        var sumFactores = calcularFactores()

        var x = 0
        var y = 0

        var nivel = arrayOf<String>("","","")
        var nivel2 = arrayOf<String>("","","","")

        var t = 1

        for(i in 1..3){
            val variables = asignarVariablesCalcularNota(i)
            x = variables!!.first
            y = variables!!.second

            if(sumFactores[t-1]<=x){
                nivel[t-1] = "bajo"
            }
            if(sumFactores[t-1]>x && sumFactores[t-1]<=y){
                nivel[t-1] = "medio"
            }
            if(sumFactores[t-1]>y){
                nivel[t-1] = "alto"
            }
            t++
        }

        /*
        sumFactores2[0]: suma de todos
        sumFactores2[1]:suma Fisiologico y Cognitivo
        sumFactores2[2]: suma Cognitivo y Evitacion
        sumFactores2[3]: suma Fisiologico y evitcion
         */

        val sumFactores2 = arrayOf<Int>(sumFactores[0]+sumFactores[1]+sumFactores[2],
            sumFactores[0]+sumFactores[1],sumFactores[1]+sumFactores[2], sumFactores[0]+sumFactores[2])

        for(j in 0..sumFactores2.size-1){
            val variables = asignarVariables2(j)
            x = variables!!.first
            y = variables!!.second

            if(sumFactores2[j]<=x){
                nivel2[j] = "bajo"
            }
            if(sumFactores2[j]>x && sumFactores2[j]<=y){
                nivel2[j] = "medio"
            }
            if(sumFactores2[j]>y){
                nivel2[j] = "alto"
            }
        }
    // yyyy-mm-dd

        return "resultado nivel 1: "+nivel[0]+", nivel 2: "+nivel[1]+", nivel 3: "+nivel[2]+"\n"+
                "Otros, todo: "+nivel2[0]+", FigCog: "+nivel2[1]+", CogEvi: "+nivel2[2]+", FigEvi: "+nivel2[3]
    }

    fun guardarResultados(factores: Array<Int>, sincronizado:Boolean){
        val formato = SimpleDateFormat("yyyy-MM-dd")
        var subido = 0
        if(sincronizado){
            subido=1
        }

        val bh = DBHelper(this)
        val db: SQLiteDatabase = bh.getWritableDatabase()
        db.beginTransaction()
        val cvalue = ContentValues()
        cvalue.put("username", userName.value)
        cvalue.put("fecha", formato.format(Date()))
        cvalue.put("factor1", factores[0])
        cvalue.put("factor2", factores[1])
        cvalue.put("factor3", factores[2])
        cvalue.put("subido", subido) // 0 indica que NO esta subido(o se entiende mejor si es 1?)
        db.insert("resultados", null, cvalue)
        db.setTransactionSuccessful()
        db.endTransaction()
        db.close()
    }



    private fun destruction() {

        if(i != 0) {
            preferencesUtil?.setNumPage(i-5)
        }else{
            preferencesUtil?.setNumPage(i)
        }
    }
}