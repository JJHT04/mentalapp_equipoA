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
        val toolbar = findViewById<Toolbar>(R.id.topAppBarAdvices).apply {
            setNavigationOnClickListener {
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        setSupportActionBar(toolbar)

        findViewById<TextView>(R.id.textViewConsejos).text = intent.getStringExtra(EXTRAMESSAGE)
    }
}