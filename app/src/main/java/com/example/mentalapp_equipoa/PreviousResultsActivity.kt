package com.example.mentalapp_equipoa

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.mentalapp_equipoa.TestActivity.Companion.asignarConsejos
import com.example.mentalapp_equipoa.TestActivity.Companion.calcularNota

class PreviousResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_results)
        val toolbar = findViewById<Toolbar>(R.id.topAppBarHistory)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            navigateUpTo(intent)
        }

        var numTest = numTestHechos()

        if(numTest==0){
            val textView = TextView(this).apply {
                text= "No hay resultados anteriores"
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
        }
        val ides = testUser()

        for (i in 0..numTest-1) {
            val factores = recogerFactores(ides[i])
            val niveles = calcularNota(factores, this)
            val consejos = asignarConsejos(calcularNota(recogerFactores(ides[i]), this), this)

            createCard("Resultado Test $i", "Factor Fisiologico: "+niveles[0]+"\n"+
                    "Factor Cognitivo: "+niveles[1]+"\n"+
                    "Factor Evitacion "+niveles[2]+"\n"+
                    consejos
                    , R.drawable.baseline_keyboard_arrow_left_24)
        }
    }

    private fun numTestHechos(): Int{
        val bh = DBHelper(this)
        val dbR: SQLiteDatabase = bh.readableDatabase
        var nombre = userName.value
        val c = dbR.rawQuery("SELECT COUNT(id) FROM Resultados WHERE username = ?", arrayOf(nombre))
        var numTest = 0

        if(c.moveToNext()){
            do{
                numTest = c.getInt(0)
            } while (c.moveToNext())
            c.close()
        }
        dbR.close()

        return numTest
    }

    private fun testUser(): ArrayList<Int>{
        val bh = DBHelper(this)
        val dbR: SQLiteDatabase = bh.readableDatabase
        var nombre = userName.value
        val c = dbR.rawQuery("SELECT id FROM Resultados WHERE username = ?", arrayOf(nombre))
        val ides = ArrayList<Int>()


        if(c.moveToFirst()){

            do{
                ides.add(c.getInt(0))
            } while (c.moveToNext())
            c.close()
        }
        dbR.close()

        return ides
    }

    fun recogerFactores(i: Int):Array<Int>{
        var factores = arrayOf<Int>(0,0,0)
        val bh = DBHelper(this)
        val dbR: SQLiteDatabase = bh.readableDatabase
        val c = dbR.rawQuery("SELECT factor1, factor2, factor3 FROM Resultados WHERE username = ? AND id = $i", arrayOf(userName.value))

        if(c.moveToNext()){
            do{
                factores[0]= c.getInt(0)
                factores[1]= c.getInt(1)
                factores[2]= c.getInt(2)
            } while (c.moveToNext())
            c.close()
        }
        dbR.close()
        return factores
    }

    private fun createCard (title: String, body: String, @DrawableRes icon: Int) {

        val cardView = CardView(this).apply {
            isClickable = true
            background = AppCompatResources.getDrawable(this@PreviousResultsActivity,R.drawable.shape_rounded_alt2)
            id = View.generateViewId()
        }

        val titleView = TextView(this).apply {
            id = View.generateViewId()
            typeface = ResourcesCompat.getFont(this@PreviousResultsActivity, R.font.lexend)
            textSize = 20F
            text = title
        }

        val iconView = ImageView(this).apply {
            id = View.generateViewId()
            setImageDrawable(AppCompatResources.getDrawable(this@PreviousResultsActivity, icon))
        }

        val bodyView = TextView(this).apply {
            id = View.generateViewId()
            typeface = ResourcesCompat.getFont(this@PreviousResultsActivity, R.font.lexend)
            textSize = 16F
            text = body
            visibility = View.GONE
        }

        val constraintLayout = ConstraintLayout(this).apply {
            View.generateViewId()
        }
        constraintLayout.addView(titleView)
        constraintLayout.addView(iconView)
        constraintLayout.addView(bodyView)

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

// Establecer las restricciones para el TextView
        constraintSet.constrainWidth(titleView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(titleView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainWidth(iconView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(iconView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainWidth(bodyView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(bodyView.id, ConstraintSet.WRAP_CONTENT)

        constraintSet.connect(
            titleView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            0 // Margen superior en píxeles
        )
        constraintSet.connect(
            titleView.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            0 // Margen izquierdo en píxeles
        )

        constraintSet.connect(
            titleView.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            0
        )

        constraintSet.connect(
            iconView.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            0
        )

        constraintSet.connect(
            iconView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )

        constraintSet.connect(
            bodyView.id,
            ConstraintSet.TOP,
            titleView.id,
            ConstraintSet.BOTTOM,
            10
        )

        constraintSet.connect(
            bodyView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )

        constraintSet.connect(
            bodyView.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            0
        )
        constraintSet.connect(
            bodyView.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            0
        )

        constraintSet.applyTo(constraintLayout)


        cardView.addView(constraintLayout)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val marginParams = layoutParams as ViewGroup.MarginLayoutParams
        marginParams.setMargins(10, 0, 10, 16) // Margen inferior entre las cartas

        val frameLayout = FrameLayout(this).apply {
            id = View.generateViewId()
        }

        frameLayout.addView(cardView)

        frameLayout.layoutParams = marginParams


        createDropDownCard(cardView, bodyView, iconView)

        findViewById<LinearLayout>(R.id.linear_previous).addView(frameLayout)
    }

    private fun createDropDownCard (card: CardView, text: TextView, icon: ImageView) {

        val transition = ChangeBounds().apply {
            duration = 300
            interpolator = BounceInterpolator()
        }

        card.setOnClickListener {
            TransitionManager.beginDelayedTransition(findViewById<LinearLayout>(R.id.linear_previous), transition)

            if (text.visibility == View.GONE) {
                text.visibility = View.VISIBLE // Mostrar la respuesta al hacer clic
                icon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.baseline_keyboard_arrow_down_24))
            } else {
                text.visibility = View.GONE // Ocultar la respuesta si ya está visible
                icon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.baseline_keyboard_arrow_left_24))
            }
        }
    }
}