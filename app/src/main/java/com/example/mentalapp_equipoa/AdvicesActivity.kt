package com.example.mentalapp_equipoa

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class AdvicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advices)
        val toolbar = findViewById<Toolbar>(R.id.topAppBarAdvices)

        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        val factores = intent.getIntArrayExtra(EXTRAMESSAGE2)

        val formatString = "RESULTADOS\nFactor Fisiológico: ${factores?.get(0)}\nFactor Cognitivo: ${factores?.get(1)}\nFactor de Evitación: ${factores?.get(2)}" +
                "\n\nCONSEJOS\n${intent.getStringExtra(EXTRAMESSAGE)}"

        findViewById<TextView>(R.id.textViewConsejos).text = formatString
    }
}