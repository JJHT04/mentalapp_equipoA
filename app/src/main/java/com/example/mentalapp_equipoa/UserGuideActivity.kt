package com.example.mentalapp_equipoa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class UserGuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_guide)

        val toolbar: Toolbar = findViewById(R.id.topAppBarUserGuide)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            navigateUpTo(intent)
        }


    }

    /* A lo mejor sirve en el futuro
    private fun createDropDownCard (card: CardView, text: TextView, icon: ImageView) {
        card.setOnClickListener {
            if (text.visibility == View.GONE) {
                text.visibility = View.VISIBLE // Mostrar la respuesta al hacer clic
                icon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.baseline_keyboard_arrow_down_24))
            } else {
                text.visibility = View.GONE // Ocultar la respuesta si ya está visible
                icon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.baseline_keyboard_arrow_left_24))
            }
        }
    }

     */
}