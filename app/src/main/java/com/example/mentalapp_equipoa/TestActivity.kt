package com.example.mentalapp_equipoa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

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
    private var respuestas =  arrayOf<Int>(2,1,4,3,0,5,1,2,4,0,3,5,1,2,3,0,4,5,1,2,3,0,4,5,1,2,3,0,4,5,1,2,3,0,4,5,1,2,3,0,4,5,1)
    private var factor = arrayOf<Int>(1, 1, 1, 2, 1, 1, 1, 3, 1, 1, 1, 2, 1, 3, 3, 3, 3, 1, 3, 2, 3, 3, 3, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 3, 3, 3, 1, 1, 3, 1, 1, 2, 3)
    // Éstos arrays, posteriormente serán rellenados siendo leídos desde un archivo .csv, el cual habrá sido creado a partir de los datos recogidos de una base de datos.
    // El posterior cambio no perjudicará el funcionamiento del programa
    private var i = 0
    private var x = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val toolbar: Toolbar = findViewById(R.id.topAppBarTest)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            navigateUpTo(Intent(this, MainActivity::class.java))
        }
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        findViewById<TextView>(R.id.txvPregunta).apply {
            text = "-"+ (i + 1) + ". " + preguntas[i]
        }

    }
    fun onRadioButtonClicked(view: View) {
        var rdb0 = findViewById<RadioButton>(R.id.rdb0)
        var rdb1 = findViewById<RadioButton>(R.id.rdb1)
        var rdb2 = findViewById<RadioButton>(R.id.rdb2)
        var rdb3 = findViewById<RadioButton>(R.id.rdb3)
        var rdb4 = findViewById<RadioButton>(R.id.rdb4)
        var rdb5 = findViewById<RadioButton>(R.id.rdb5)
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.rdb0 ->
                    if (checked) {
                        x = 0
                    }
                R.id.rdb1 ->
                    if (checked) {
                        x = 1
                    }
                R.id.rdb2 ->
                    if (checked) {
                        x = 2
                    }
                R.id.rdb3 ->
                    if (checked) {
                        x = 3
                    }
                R.id.rdb4 ->
                    if (checked) {
                        x = 4
                    }
                R.id.rdb5 ->
                    if (checked) {
                        x = 5
                    }

            }
        }
    }
    fun btnSiguienteOnClick(view: View) {
        if (i < preguntas.size - 1) {
            findViewById<TextView>(R.id.txvAlerta).apply {
                text = ""
            }
            if(x != -1){
                var rdb0 = findViewById<RadioButton>(R.id.rdb0)
                var rdb1 = findViewById<RadioButton>(R.id.rdb1)
                var rdb2 = findViewById<RadioButton>(R.id.rdb2)
                var rdb3 = findViewById<RadioButton>(R.id.rdb3)
                var rdb4 = findViewById<RadioButton>(R.id.rdb4)
                var rdb5 = findViewById<RadioButton>(R.id.rdb5)
                if(rdb0.isChecked){
                    rdb0.isChecked = false
                }else if(rdb1.isChecked){
                    rdb1.isChecked = false
                }else if(rdb2.isChecked){
                    rdb2.isChecked = false
                }else if(rdb3.isChecked){
                    rdb3.isChecked = false
                }else if(rdb4.isChecked){
                    rdb4.isChecked = false
                }else if(rdb5.isChecked){
                    rdb5.isChecked = false
                }
                respuestas[i] = x
                x = -1
                i += 1
                findViewById<TextView>(R.id.txvPregunta).apply {
                    text = "-"+ (i + 1) + ". " + preguntas[i]
                }
            }else{
                findViewById<TextView>(R.id.txvAlerta).apply {
                    text = "Tienes que seleccionar una respuesta"
                }
            }


        } else {
            findViewById<TextView>(R.id.txvAlerta).apply {
                text = "Has completado el test de salud mental, felicidades!!"
            }
        }
    }
    fun btnAnteriorOnClick(view: View) {
        if (i > 0) {
            findViewById<TextView>(R.id.txvAlerta).apply {
                text = ""
            }


            i -= 1
            if(respuestas[i] == 0){
                findViewById<RadioButton>(R.id.rdb0).apply {
                    isChecked = true
                }
            }else if(respuestas[i] == 1){
                findViewById<RadioButton>(R.id.rdb1).apply {
                    isChecked = true
                }
            }else if(respuestas[i] == 2){
                findViewById<RadioButton>(R.id.rdb2).apply {
                    isChecked = true
                }
            }else if(respuestas[i] == 3){
                findViewById<RadioButton>(R.id.rdb3).apply {
                    isChecked = true
                }
            }else if(respuestas[i] == 4){
                findViewById<RadioButton>(R.id.rdb4).apply {
                    isChecked = true
                }
            }else if(respuestas[i] == 5){
                findViewById<RadioButton>(R.id.rdb5).apply {
                    isChecked = true
                }
            }
            x = respuestas[i]
            findViewById<TextView>(R.id.txvPregunta).apply {
                text = "-"+ (i + 1) + ". " + preguntas[i]
            }
        } else {
            // Mensaje o acción cuando no hay más preguntas
            findViewById<TextView>(R.id.txvAlerta).apply {
                text = "No hay una pregunta anterior"
            }
        }
    }
}