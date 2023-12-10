package com.example.mentalapp_equipoa

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import com.example.mentalapp_equipoa.dialogs.LoginUserDialog
import com.example.mentalapp_equipoa.dialogs.ModifyUserDialog
import com.example.mentalapp_equipoa.dialogs.RegisterUserDialog
import com.example.mentalapp_equipoa.dialogs.TestDialog
import com.example.mentalapp_equipoa.enums.Gender

val previous_results = ArrayList<String>()
var userName = MutableLiveData<String>()
var userAge: Int? = null
var userGender: Gender? = null
private const val TAG = "MainActivity"
const val EXTRA_MESSAGE = "com.example.mentalapp_equipoa.MESSAGE"
private var dataChanged: Boolean = false

fun showToast (context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
fun showToast (context: Context, @StringRes id: Int) = Toast.makeText(context, id, Toast.LENGTH_SHORT).show()
class MainActivity : AppCompatActivity() {
    private lateinit var preferencesUtil: PreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        preferencesUtil = PreferencesUtil(this)

        userName.observe(this){
            if (it != null) {
                supportActionBar?.title = it
                supportActionBar?.subtitle = "$userAge ${getString(R.string.years_old)}"

                val originalDrawable = getIconHappy(this)
                // Redimensiona el Drawable creando un nuevo Bitmap con las dimensiones deseadas
                val resizedBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(resizedBitmap)
                originalDrawable?.setBounds(0, 0, canvas.width, canvas.height)
                originalDrawable?.draw(canvas)

                // Crea un nuevo Drawable a partir del Bitmap redimensionado
                val resizedDrawable = BitmapDrawable(resources, resizedBitmap)
                supportActionBar?.setIcon(resizedDrawable)
            }
        }

        userName.value = preferencesUtil.getUsername()
        userAge = preferencesUtil.getAge()
        userGender = preferencesUtil.getGender()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.optionsmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.optionsOpt1 -> {

                true
            }
            R.id.registrar -> {
                RegisterUserDialog().show(supportFragmentManager, "registerDialog")
                true
            }
            R.id.modificar -> {
                ModifyUserDialog().show(supportFragmentManager, "modifyDialog")
                true
            }
            R.id.acceder -> {
                LoginUserDialog().show(supportFragmentManager, "loginDialog")
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun btnUserGuideOnClick(view: View) {
        val intent = Intent(this, UserGuideActivity::class.java)
        startActivity(intent)
    }

    fun btnTestOnClick(view: View) {
        TestDialog(getString(R.string.information_test_dialog),
            getString(R.string.test_how_it_works), R.drawable.baseline_info_24).show(supportFragmentManager, "test01")
    }

    fun btnPreviousOnClick(view: View) {
        val intent = Intent(this, PreviousResultsActivity::class.java)
        startActivity(intent)
    }

}