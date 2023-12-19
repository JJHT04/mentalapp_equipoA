package com.example.mentalapp_equipoa

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager

class UserGuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_guide)

        val toolbar: Toolbar = findViewById(R.id.topAppBarUserGuide)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            navigateUpTo(intent)
        }

        val drawableRes = R.drawable.baseline_keyboard_arrow_left_24

        createCard(getString(R.string.connectHeader), getString(R.string.infoUserButton), drawableRes)
        createCard(getString(R.string.registerHeader), getString(R.string.registerInfo), drawableRes)
        createCard(getString(R.string.userInfoHeader), getString(R.string.userInfoInfo), drawableRes)
        createCard(getString(R.string.startText), getString(R.string.beginTestHeader), drawableRes)
        createCard(getString(R.string.previousResultHeader), getString(R.string.previousResultInfo), drawableRes)
    }

    private fun createCard (title: String, body: CharSequence, @DrawableRes icon: Int) {

        val cardView = CardView(this).apply {
            isClickable = true
            background = AppCompatResources.getDrawable(this@UserGuideActivity,R.drawable.shape_rounded_alt2)
            id = View.generateViewId()
        }

        val titleView = TextView(this).apply {
            id = View.generateViewId()
            typeface = ResourcesCompat.getFont(this@UserGuideActivity, R.font.lexend)
            textSize = 18F
            text = title
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        val iconView = ImageView(this).apply {
            id = View.generateViewId()
            setImageDrawable(AppCompatResources.getDrawable(this@UserGuideActivity, icon))
        }

        val bodyView = TextView(this).apply {
            id = View.generateViewId()
            typeface = ResourcesCompat.getFont(this@UserGuideActivity, R.font.lexend)
            textSize = 16F
            text = body
            visibility = View.GONE
        }

        val constraintLayout = ConstraintLayout(this).apply {
            id = View.generateViewId()
        }

        constraintLayout.addView(titleView)
        constraintLayout.addView(iconView)
        constraintLayout.addView(bodyView)

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        constraintSet.constrainWidth(titleView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(titleView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainWidth(iconView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(iconView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainWidth(bodyView.id, ConstraintSet.MATCH_CONSTRAINT)
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
            10 // Margen izquierdo en píxeles
        )

        constraintSet.connect(
            titleView.id,
            ConstraintSet.END,
            iconView.id,
            ConstraintSet.START,
            10
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
            20
        )

        constraintSet.connect(
            bodyView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            30
        )

        constraintSet.connect(
            bodyView.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            20
        )
        constraintSet.connect(
            bodyView.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            20
        )

        constraintSet.applyTo(constraintLayout)


        cardView.addView(constraintLayout)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val marginParams = layoutParams as ViewGroup.MarginLayoutParams
        marginParams.setMargins(10, 0, 10, 16) // Margen inferior entre las cartas


        cardView.layoutParams = marginParams


        createDropDownCard(cardView, bodyView, iconView)

        findViewById<LinearLayout>(R.id.userGuideLinear).addView(cardView)
    }

    private fun createDropDownCard (card: CardView, text: TextView, icon: ImageView) {

        val transition = ChangeBounds().apply {
            duration = 300
            interpolator = BounceInterpolator()
        }

        card.setOnClickListener {
            TransitionManager.beginDelayedTransition(findViewById<LinearLayout>(R.id.userGuideLinear), transition)

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