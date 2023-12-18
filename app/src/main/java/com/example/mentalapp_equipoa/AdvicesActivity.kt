package com.example.mentalapp_equipoa

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
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

        val notitas = intent.getStringArrayExtra(EXTRAMESSAGE2)

        val formatString = "<h2>RESULTADOS</h2><p>Factor Fisiológico: ${notitas?.get(0)}</p> <p>Factor Cognitivo: ${notitas?.get(1)}</p> <p>Factor de Evitación: ${notitas?.get(2)}</p>" +
                "<br><br><h2>CONSEJOS</h2>${intent.getStringExtra(EXTRAMESSAGE)}"

        val textView = findViewById<TextView>(R.id.textViewConsejos)
        textView.text = Html.fromHtml(formatString, Html.FROM_HTML_MODE_COMPACT)
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}