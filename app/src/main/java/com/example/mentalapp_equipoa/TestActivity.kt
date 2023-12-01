package com.example.mentalapp_equipoa

import android.annotation.SuppressLint
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
var respuestas=Array<Int?>(44){null}
var factor = arrayOf<Int>(1, 1, 1, 2, 1, 1, 1, 3, 1, 1, 1, 2, 1, 3, 3, 3, 3, 1, 3, 2, 3, 3, 3, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 3, 3, 3, 1, 1, 3, 1, 1, 2, 3)

class TestActivity : AppCompatActivity() {

    private var preguntas =  arrayOf<String>("En los exámenes me sudan las manos.",
        "Cuando llevo rato haciendo el examen, siento molestias en el estómago y necesidad de defecar.",
        "Al comenzar a leer el examen se me nubla la vista y no entiendo lo que leo.",
        "Si llego 5 minutos tarde a un examen ya no entro, me escondo por el instituto o intento marcharme.",
        "Las condiciones donde se realiza un examen (mucho ruido, calor, frío, sol…) me influyen aumentando mi nerviosismo.",
        "Cuando termino un examen me duele la cabeza.",
        "Cuando llevo un rato haciendo el examen, yo siento que me falta el aire, mucho calor y sensación de que me voy a desmayar.",
        "Me siento nervioso/a si el profesor/a se para junto a mí y ya no puedo seguir contestando.",
        "Me pongo nervioso/a al ver al profesor/a con los exámenes antes de entrar al aula.",
        "En el examen siento rígidas las manos y/o los brazos.",
        "Antes de entrar al examen siento un nudo en el estómago, que desaparece al comenzar a escribir.",
        "Al comenzar el examen, nada más leer las preguntas lo entrego al profesor/a en blanco y vuelvo a mi sitio.",
        "Después del examen lloro con facilidad, al pensar lo mal que lo he hecho aunque no sepa el resultado.",
        "Mientras que estoy realizando el examen, pienso que lo estoy haciendo muy mal.",
        "Me siento nervioso/a si los demás comienzan a entregar el examen antes que yo.",
        "Pienso que el profesor/a me está observando constantemente.",
        "Suelo morderme las uñas o el bolígrafo durante los exámenes.",
        "Tengo muchas ganas de ir al cuarto de baño durante el examen.",
        "No puedo quedarme quieto/a mientras hago el examen (muevo los pies, el bolígrafo, miro alrededor, miro la hora…).",
        "Me pongo malo/a y doy excusas para no hacer un examen.",
        "Para mí supone una tranquilidad o alivio cuando por cualquier razón, se aplaza un examen.",
        "Pienso que no voy a poder aprobar el examen, aunque haya estudiado.",
        "Antes de hacer el examen pienso que no me acuerdo de nada y voy a suspenderlo.",
        "No consigo dormir la noche anterior a un examen.",
        "Me pone nervioso/a que el aula esté llena de gente.",
        "He sentido mareos y ganas de vomitar en un examen.",
        "Momentos antes de hacer el examen tengo la boca seca y me cuesta tragar.",
        "Si me siento en las primeras filas para hacer el examen, aumenta mi nerviosismo.",
        "Si el examen tiene un tiempo fijo para realizarse, aumenta mi nerviosismo y lo hago peor.",
        "Me siento nervioso/a en aulas muy grandes.",
        "Cuando estoy haciendo un examen el corazón me late muy deprisa.",
        "Al entrar en el aula donde se hace el examen me tiemblan las piernas.",
        "Me siento nervioso/a en las clases demasiado pequeñas.",
        "Cuando un grupo de compañeros habla del examen antes de que venga el profesor me pongo más nervioso/a.",
        "Al salir del examen, tengo la sensación de haberlo hecho muy mal.",
        "Pienso que me voy a poner nervioso/a y se me va a olvidar todo.",
        "Tardo mucho tiempo en decidirme por contestar la mayoría de las preguntas o por entregar el examen.",
        "Me resulta difícil o imposible ingerir alimentos antes de realizar un examen.",
        "En las horas/momentos previos al examen siento molestias en el estómago o se me descompone y tengo que ir al baño.",
        "Desarrollo un ritual de comportamiento que me da seguridad y cuando algo me falla pienso que el examen se me va a dar mal.",
        "Antes de entrar al examen hablo con mucha rapidez y me muevo más de lo habitual.",
        "La noche anterior al examen o la misma mañana me asaltan pensamientos de inseguridad aunque lo haya trabajado e incluso lloro con facilidad.",
        "Establezco un ritual previo al examen y si no puedo cumplirlo, busco alguna excusa y no entro al examen.",
        "Tras haber realizado un examen siento que lo he hecho fatal y pienso en desaparecer o hacerme daño."
    )
    //private var respuestas =  arrayOf<Int>(2,1,4,3,0,5,1,2,4,0,3,5,1,2,3,0,4,5,1,2,3,0,4,5,1,2,3,0,4,5,1,2,3,0,4,5,1,2,3,0,4,5,1)
    // Éstos arrays, posteriormente serán rellenados siendo leídos desde un archivo .csv, el cual habrá sido creado a partir de los datos recogidos de una base de datos.
    // El posterior cambio no perjudicará el funcionamiento del programa
    private var i = 0
    private var x = -1
    private var y = -1
    private var z = -1
    private var l = -1
    private var k = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val toolbar: Toolbar = findViewById(R.id.topAppBarTest)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            navigateUpTo(Intent(this, MainActivity::class.java))
        }
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        findViewById<TextView>(R.id.txvPregunta1).apply {
            text = "-"+ (i + 1) + ". " + preguntas[i]
        }
        findViewById<TextView>(R.id.txvPregunta2).apply {
            text = "-"+ (i + 2) + ". " + preguntas[i+1]
        }
        findViewById<TextView>(R.id.txvPregunta3).apply {
            text = "-"+ (i + 3) + ". " + preguntas[i+2]
        }
        findViewById<TextView>(R.id.txvPregunta4).apply {
            text = "-"+ (i + 4) + ". " + preguntas[i+3]
        }
        findViewById<TextView>(R.id.txvPregunta5).apply {
            text = "-"+ (i + 5) + ". " + preguntas[i+4]
        }

    }

    fun onRadioButtonClicked(view: View) {
        var rdb1 = findViewById<RadioButton>(R.id.rdb1)
        var rdb2 = findViewById<RadioButton>(R.id.rdb2)
        var rdb3 = findViewById<RadioButton>(R.id.rdb3)
        var rdb4 = findViewById<RadioButton>(R.id.rdb4)
        var rdb5 = findViewById<RadioButton>(R.id.rdb5)
        var rdb6 = findViewById<RadioButton>(R.id.rdb6)
        var rdb7 = findViewById<RadioButton>(R.id.rdb7)
        var rdb8 = findViewById<RadioButton>(R.id.rdb8)
        var rdb9 = findViewById<RadioButton>(R.id.rdb9)
        var rdb10 = findViewById<RadioButton>(R.id.rdb10)
        var rdb11 = findViewById<RadioButton>(R.id.rdb11)
        var rdb12 = findViewById<RadioButton>(R.id.rdb12)
        var rdb13 = findViewById<RadioButton>(R.id.rdb13)
        var rdb14 = findViewById<RadioButton>(R.id.rdb14)
        var rdb15 = findViewById<RadioButton>(R.id.rdb15)
        var rdb16 = findViewById<RadioButton>(R.id.rdb16)
        var rdb17 = findViewById<RadioButton>(R.id.rdb17)
        var rdb18 = findViewById<RadioButton>(R.id.rdb18)
        var rdb19 = findViewById<RadioButton>(R.id.rdb19)
        var rdb20 = findViewById<RadioButton>(R.id.rdb20)
        var rdb21 = findViewById<RadioButton>(R.id.rdb21)
        var rdb22 = findViewById<RadioButton>(R.id.rdb22)
        var rdb23 = findViewById<RadioButton>(R.id.rdb23)
        var rdb24 = findViewById<RadioButton>(R.id.rdb24)
        var rdb25 = findViewById<RadioButton>(R.id.rdb25)


        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.rdb1 ->
                    if (checked) {
                        x = 0
                    }
                R.id.rdb2 ->
                    if (checked) {
                        x = 1
                    }
                R.id.rdb3 ->
                    if (checked) {
                        x = 2
                    }
                R.id.rdb4 ->
                    if (checked) {
                        x = 3
                    }
                R.id.rdb5 ->
                    if (checked) {
                        x = 4
                    }
                R.id.rdb6 ->
                    if (checked) {
                        y = 0
                    }
                R.id.rdb7 ->
                    if (checked) {
                        y = 1
                    }
                R.id.rdb8 ->
                    if (checked) {
                        y = 2
                    }
                R.id.rdb9 ->
                    if (checked) {
                        y = 3
                    }
                R.id.rdb10 ->
                    if (checked) {
                        y = 4
                    }
                R.id.rdb11 ->
                    if (checked) {
                        z = 0
                    }
                R.id.rdb12 ->
                    if (checked) {
                        z = 1
                    }
                R.id.rdb13 ->
                    if (checked) {
                        z = 2
                    }
                R.id.rdb14 ->
                    if (checked) {
                        z = 3
                    }
                R.id.rdb15 ->
                    if (checked) {
                        z = 4
                    }
                R.id.rdb16 ->
                    if (checked) {
                        l = 0
                    }
                R.id.rdb17 ->
                    if (checked) {
                        l = 1
                    }
                R.id.rdb18 ->
                    if (checked) {
                        l = 2
                    }
                R.id.rdb19 ->
                    if (checked) {
                        l = 3
                    }
                R.id.rdb20 ->
                    if (checked) {
                        l = 4
                    }
                R.id.rdb21 ->
                    if (checked) {
                        k = 0
                    }
                R.id.rdb22 ->
                    if (checked) {
                        k = 1
                    }
                R.id.rdb23 ->
                    if (checked) {
                        k = 2
                    }
                R.id.rdb24 ->
                    if (checked) {
                        k = 3
                    }
                R.id.rdb25 ->
                    if (checked) {
                        k = 4
                    }

            }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun btnSiguienteOnClick(view: View) {
        if (i < (preguntas.size-preguntas.size%5)) {
                findViewById<TextView>(R.id.txvAlerta).apply {
                    text = ""
                }
            findViewById<RadioGroup>(R.id.radioGroup4).apply {
                visibility = View.VISIBLE
            }
            findViewById<RadioGroup>(R.id.radioGroup3).apply {
                visibility = View.VISIBLE
            }
            findViewById<RadioGroup>(R.id.radioGroup2).apply {
                visibility = View.VISIBLE
            }
            findViewById<RadioGroup>(R.id.radioGroup1).apply {
                visibility = View.VISIBLE
            }
            if (i+9 < preguntas.size -1) {
                if (!(x == -1 || y==-1 || z==-1 || l==-1 || k==-1)) {

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

                    respuestas[i] = x
                    respuestas[i+1] = y
                    respuestas[i+2] = z
                    respuestas[i+3] = l
                    respuestas[i+4] = k
                    x = -1
                    y = -1
                    z = -1
                    l = -1
                    k = -1
                    i += 5

                        findViewById<TextView>(R.id.txvPregunta1).apply {
                            text = "-" + (i + 1) + ". " + preguntas[i]
                        }
                        findViewById<TextView>(R.id.txvPregunta2).apply {
                            text = "-" + (i + 2) + ". " + preguntas[i + 1]
                        }
                        findViewById<TextView>(R.id.txvPregunta3).apply {
                            text = "-" + (i + 3) + ". " + preguntas[i + 2]
                        }
                        findViewById<TextView>(R.id.txvPregunta4).apply {
                            text = "-" + (i + 4) + ". " + preguntas[i + 3]
                        }
                        findViewById<TextView>(R.id.txvPregunta5).apply {
                            text = "-" + (i + 5) + ". " + preguntas[i + 4]
                        }
                }else {
                    findViewById<TextView>(R.id.txvAlerta).apply {
                        text = "Tienes que seleccionar una respuesta para cada pregunta"
                    }
                }
            }else {
                findViewById<Button>(R.id.btnSiguiente).apply {
                    text= "Mostrar resultados"
                }
                findViewById<Button>(R.id.btnAnterior).apply {
                    isEnabled = false
                }
                var num = preguntas.size - 1 - (i+4)
                if (num == 1) {
                    if (!(x == -1 || y==-1 || z==-1 || l==-1 || k==-1)) {

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
                        respuestas[i] = x
                        x = -1
                        y = -1
                        z = -1
                        l = -1
                        k = -1
                        i += 5

                        findViewById<TextView>(R.id.txvPregunta1).apply {
                            text = "-" + (i + 1) + ". " + preguntas[i]
                        }
                        findViewById<TextView>(R.id.txvPregunta2).apply {
                            text = ""
                        }
                        findViewById<TextView>(R.id.txvPregunta3).apply {
                            text = ""
                        }
                        findViewById<TextView>(R.id.txvPregunta4).apply {
                            text = ""
                        }
                        findViewById<TextView>(R.id.txvPregunta5).apply {
                            text = ""
                        }
                        findViewById<RadioGroup>(R.id.radioGroup2).apply {
                            visibility = View.INVISIBLE
                        }
                        findViewById<RadioGroup>(R.id.radioGroup3).apply {
                            visibility = View.INVISIBLE
                        }
                        findViewById<RadioGroup>(R.id.radioGroup4).apply {
                            visibility = View.INVISIBLE
                        }
                        findViewById<RadioGroup>(R.id.radioGroup5).apply {
                            visibility = View.INVISIBLE
                        }
                    }
                } else if (num == 2) {
                    if (!(x == -1 || y==-1 || z==-1 || l==-1 || k==-1)) {

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
                        respuestas[i] = x
                        respuestas[i+1] = y
                        x = -1
                        y = -1
                        z = -1
                        l = -1
                        k = -1
                        i += 5

                        findViewById<TextView>(R.id.txvPregunta1).apply {
                            text = "-" + (i + 1) + ". " + preguntas[i]
                        }
                        findViewById<TextView>(R.id.txvPregunta2).apply {
                            text = "-" + (i + 2) + ". " + preguntas[i + 1]
                        }
                        findViewById<TextView>(R.id.txvPregunta3).apply {
                            text = ""
                        }
                        findViewById<TextView>(R.id.txvPregunta4).apply {
                            text = ""
                        }
                        findViewById<TextView>(R.id.txvPregunta5).apply {
                            text = ""
                        }
                        findViewById<RadioGroup>(R.id.radioGroup3).apply {
                            visibility = View.INVISIBLE
                        }
                        findViewById<RadioGroup>(R.id.radioGroup4).apply {
                            visibility = View.INVISIBLE
                        }
                        findViewById<RadioGroup>(R.id.radioGroup5).apply {
                            visibility = View.INVISIBLE
                        }
                    }else {
                        findViewById<TextView>(R.id.txvAlerta).apply {
                            text = "Tienes que seleccionar una respuesta para cada pregunta"
                        }
                    }
                } else if (num == 3) {
                    if (!(x == -1 || y==-1 || z==-1 || l==-1 || k==-1)) {

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
                        respuestas[i] = x
                        respuestas[i+1] = y
                        respuestas[i+2] = z
                        x = -1
                        y = -1
                        z = -1
                        l = -1
                        k = -1
                        i += 5

                        findViewById<TextView>(R.id.txvPregunta1).apply {
                            text = "-" + (i + 1) + ". " + preguntas[i]
                        }
                        findViewById<TextView>(R.id.txvPregunta2).apply {
                            text = "-" + (i + 2) + ". " + preguntas[i + 1]
                        }
                        findViewById<TextView>(R.id.txvPregunta3).apply {
                            text = "-" + (i + 3) + ". " + preguntas[i + 2]
                        }
                        findViewById<TextView>(R.id.txvPregunta4).apply {
                            text = ""
                        }
                        findViewById<TextView>(R.id.txvPregunta5).apply {
                            text = ""
                        }
                        findViewById<RadioGroup>(R.id.radioGroup4).apply {
                            visibility = View.INVISIBLE
                        }
                        findViewById<RadioGroup>(R.id.radioGroup5).apply {
                            visibility = View.INVISIBLE
                        }
                    }else {
                        findViewById<TextView>(R.id.txvAlerta).apply {
                            text = "Tienes que seleccionar una respuesta para cada pregunta"
                        }
                    }
                } else if (num == 4) {
                    if (!(x == -1 || y==-1 || z==-1 || l==-1 || k==-1)) {

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
                        respuestas[i] = x
                        respuestas[i+1] = y
                        respuestas[i+2] = z
                        respuestas[i+3] = l
                        x = -1
                        y = -1
                        z = -1
                        l = -1
                        k = -1
                        i += 5

                        findViewById<TextView>(R.id.txvPregunta1).apply {
                            text = "-" + (i + 1) + ". " + preguntas[i]
                        }
                        findViewById<TextView>(R.id.txvPregunta2).apply {
                            text = "-" + (i + 2) + ". " + preguntas[i + 1]
                        }
                        findViewById<TextView>(R.id.txvPregunta3).apply {
                            text = "-" + (i + 3) + ". " + preguntas[i + 2]
                        }
                        findViewById<TextView>(R.id.txvPregunta4).apply {
                            text = "-" + (i + 4) + ". " + preguntas[i + 3]
                        }
                        findViewById<TextView>(R.id.txvPregunta5).apply {
                            text = ""
                        }
                        findViewById<RadioGroup>(R.id.radioGroup5).apply {
                            visibility = View.INVISIBLE
                        }
                    }else {
                        findViewById<TextView>(R.id.txvAlerta).apply {
                            text = "Tienes que seleccionar una respuesta para cada pregunta"
                        }
                    }
                }
            }


        }else{
            var num = (preguntas.size%5)
            if (num == 0){
                num = 5
            }
            var mostrar = true

                for (i in 1..num) {
                    val radioGroup = findViewById<RadioGroup>(
                        resources.getIdentifier(
                            "radioGroup$i",
                            "id",
                            packageName
                        )

                    )
                    // Obtener el ID del RadioButton seleccionado en el RadioGroup
                    val radioButtonId = radioGroup.checkedRadioButtonId

                    if (radioButtonId != -1) {
                        // Al menos un RadioButton está seleccionado
                        val radioButton = findViewById<RadioButton>(radioButtonId)
                        // Puedes hacer algo con el RadioButton seleccionado, si es necesario
                    } else {
                        mostrar = false
                    }

                }

                if (mostrar) {
                    findViewById<TextView>(R.id.txvAlerta).apply {
                        text = calcularNota()
                    }
                } else {
                    findViewById<TextView>(R.id.txvAlerta).apply {
                        text = "Contesta las últimas preguntas y podrás ver los resultados!!"
                    }
                }


        }
    }
    fun btnAnteriorOnClick(view: View) {

        if (i > 0 && i+5 < preguntas.size) {
            findViewById<RadioGroup>(R.id.radioGroup5).apply {
                visibility = View.VISIBLE
            }
            findViewById<RadioGroup>(R.id.radioGroup4).apply {
                visibility = View.VISIBLE
            }
            findViewById<RadioGroup>(R.id.radioGroup3).apply {
                visibility = View.VISIBLE
            }
            findViewById<RadioGroup>(R.id.radioGroup2).apply {
                visibility = View.VISIBLE
            }
            findViewById<TextView>(R.id.txvAlerta).apply {
                text = ""
            }

            i -= 5
            if(respuestas[i] == 0){
                findViewById<RadioButton>(R.id.rdb1).apply {
                    isChecked = true
                }
            }else if(respuestas[i] == 1){
                findViewById<RadioButton>(R.id.rdb2).apply {
                    isChecked = true
                }
            }else if(respuestas[i] == 2){
                findViewById<RadioButton>(R.id.rdb3).apply {
                    isChecked = true
                }
            }else if(respuestas[i] == 3){
                findViewById<RadioButton>(R.id.rdb4).apply {
                    isChecked = true
                }
            }else if(respuestas[i] == 4){
                findViewById<RadioButton>(R.id.rdb5).apply {
                    isChecked = true
                }
            }

            if(respuestas[i+1] == 0){
                findViewById<RadioButton>(R.id.rdb6).apply {
                    isChecked = true
                }
            }else if(respuestas[i+1] == 1){
                findViewById<RadioButton>(R.id.rdb7).apply {
                    isChecked = true
                }
            }else if(respuestas[i+1] == 2){
                findViewById<RadioButton>(R.id.rdb8).apply {
                    isChecked = true
                }
            }else if(respuestas[i+1] == 3){
                findViewById<RadioButton>(R.id.rdb9).apply {
                    isChecked = true
                }
            }else if(respuestas[i+1] == 4){
                findViewById<RadioButton>(R.id.rdb10).apply {
                    isChecked = true
                }
            }

            if(respuestas[i+2] == 0){
                findViewById<RadioButton>(R.id.rdb11).apply {
                    isChecked = true
                }
            }else if(respuestas[i+2] == 1){
                findViewById<RadioButton>(R.id.rdb12).apply {
                    isChecked = true
                }
            }else if(respuestas[i+2] == 2){
                findViewById<RadioButton>(R.id.rdb13).apply {
                    isChecked = true
                }
            }else if(respuestas[i+2] == 3){
                findViewById<RadioButton>(R.id.rdb14).apply {
                    isChecked = true
                }
            }else if(respuestas[i+2] == 4){
                findViewById<RadioButton>(R.id.rdb15).apply {
                    isChecked = true
                }
            }

            if(respuestas[i+3] == 0){
                findViewById<RadioButton>(R.id.rdb16).apply {
                    isChecked = true
                }
            }else if(respuestas[i+3] == 1){
                findViewById<RadioButton>(R.id.rdb17).apply {
                    isChecked = true
                }
            }else if(respuestas[i+3] == 2){
                findViewById<RadioButton>(R.id.rdb18).apply {
                    isChecked = true
                }
            }else if(respuestas[i+3] == 3){
                findViewById<RadioButton>(R.id.rdb19).apply {
                    isChecked = true
                }
            }else if(respuestas[i+3] == 4){
                findViewById<RadioButton>(R.id.rdb20).apply {
                    isChecked = true
                }
            }

                if(respuestas[i+4] == 0){
                    findViewById<RadioButton>(R.id.rdb21).apply {
                        isChecked = true
                    }
                }else if(respuestas[i+4] == 1){
                    findViewById<RadioButton>(R.id.rdb22).apply {
                        isChecked = true
                    }
                }else if(respuestas[i+4] == 2){
                    findViewById<RadioButton>(R.id.rdb23).apply {
                        isChecked = true
                    }
                }else if(respuestas[i+4] == 3){
                    findViewById<RadioButton>(R.id.rdb24).apply {
                        isChecked = true
                    }
                }else if(respuestas[i+4] == 4){
                    findViewById<RadioButton>(R.id.rdb25).apply {
                        isChecked = true
                    }
                }

            x = respuestas[i]!!
            y = respuestas[i+1]!!
            z = respuestas[i+2]!!
            l = respuestas[i+3]!!
            k = respuestas[i+4]!!
            findViewById<TextView>(R.id.txvPregunta1).apply {
                text = "-"+ (i + 1) + ". " + preguntas[i]
            }
            findViewById<TextView>(R.id.txvPregunta2).apply {
                text = "-"+ (i + 2) + ". " + preguntas[i+2]
            }
            findViewById<TextView>(R.id.txvPregunta3).apply {
                text = "-"+ (i + 3) + ". " + preguntas[i+3]
            }
            findViewById<TextView>(R.id.txvPregunta4).apply {
                text = "-"+ (i + 4) + ". " + preguntas[i+3]
            }
            findViewById<TextView>(R.id.txvPregunta5).apply {
                text = "-"+ (i + 5) + ". " + preguntas[i+4]
            }
        } else if (i==0){
            // Mensaje o acción cuando no hay más preguntas
            findViewById<TextView>(R.id.txvAlerta).apply {
                text = "No hay una preguntas anterior"
            }
        } else{
            findViewById<TextView>(R.id.txvAlerta).apply {
                text = "Es la última página, no hay vuelta atrás"
            }
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
            t++
        }
        return "Factor 1: " + nivel[0] +", Factor 2: " + nivel[1] + ", Factor 3: " + nivel[2]
    }
}