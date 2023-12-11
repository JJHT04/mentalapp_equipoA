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
import android.widget.Button
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import com.example.mentalapp_equipoa.dialogs.GenericDialog
import com.example.mentalapp_equipoa.dialogs.LoginUserDialog
import com.example.mentalapp_equipoa.dialogs.ModifyUserDialog
import com.example.mentalapp_equipoa.dialogs.RegisterUserDialog
import com.example.mentalapp_equipoa.dialogs.TestDialog
import com.example.mentalapp_equipoa.enums.Gender

val previous_results = ArrayList<String>()
var userName = MutableLiveData<String>()
var userAge: Int? = null
var userGender: Gender? = null
val numPag = MutableLiveData<Int>()
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
                userAge = preferencesUtil.getAge()
                userGender = preferencesUtil.getGender()
                supportActionBar?.title = it
                supportActionBar?.subtitle = "$userAge ${getString(R.string.years_old)}"

                val originalDrawable = getIconHappy(this)
                // Redimensiona el Drawable creando un nuevo Bitmap con las dimensiones deseadas
                val resizedBitmap = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(resizedBitmap)
                originalDrawable?.setBounds(0, 0, canvas.width, canvas.height)
                originalDrawable?.draw(canvas)

                // Crea un nuevo Drawable a partir del Bitmap redimensionado
                val resizedDrawable = BitmapDrawable(resources, resizedBitmap)
                supportActionBar?.setIcon(resizedDrawable)
            }
        }

        userName.value = preferencesUtil.getUsername()

        numPag.observe(this) {
            if (it > 0) {
                findViewById<Button>(R.id.btnForm).text = getString(R.string.resume_test)
            } else {
                findViewById<Button>(R.id.btnForm).text = getString(R.string.take_test)
            }
        }

        numPag.value = preferencesUtil.getNumPage()

        val con:ConexionFirebase = ConexionFirebase()
        if (TestCon.hayConexion()){
            con.sincronizarLocalFirebase(this)
        }
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
                if (userName.value != null) {
                    ModifyUserDialog().show(supportFragmentManager, "modifyDialog")
                } else {
                    GenericDialog.showGenericDialog(supportFragmentManager, "Aviso", "No tienes ningún usuario registrado en este dispositivo", AppCompatResources.getDrawable(this,R.drawable.baseline_info_24))
                }
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
        if (userName.value != null) {
            TestDialog(getString(R.string.information_test_dialog),
                getString(R.string.comoFuncionaDialog), R.drawable.baseline_info_24).show(supportFragmentManager, "test01")
        } else {
            GenericDialog.showGenericDialog(supportFragmentManager, "Inicio de sesión requerido", "Debes de iniciar sesión o registrarte para hacer el test", AppCompatResources.getDrawable(this, R.drawable.baseline_info_24))
        }
    }

    fun btnPreviousOnClick(view: View) {
        val intent = Intent(this, PreviousResultsActivity::class.java)
        startActivity(intent)
    }

}