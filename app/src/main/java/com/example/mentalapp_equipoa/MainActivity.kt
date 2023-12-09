package com.example.mentalapp_equipoa

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mentalapp_equipoa.dialogs.LoginUserDialog
import com.example.mentalapp_equipoa.dialogs.ModifyUserDialog
import com.example.mentalapp_equipoa.dialogs.RegisterUserDialog
import com.example.mentalapp_equipoa.dialogs.PreviousDialog
import com.example.mentalapp_equipoa.dialogs.TestDialog
import com.example.mentalapp_equipoa.TestActivity

val previous_results = ArrayList<String>()
var userName: String? = null
var userAge: Int? = null
var userGender: String? = null
private const val TAG = "MainActivity"
const val EXTRA_MESSAGE = "com.example.mentalapp_equipoa.MESSAGE"
class MainActivity : AppCompatActivity() {
    private var preferencesUtil: PreferencesUtil? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Ya no se usa
        setSupportActionBar(findViewById(R.id.topAppBar))

        userName = preferencesUtil?.getUsername()
        userAge = preferencesUtil?.getAge()
        userGender = preferencesUtil?.getGender()

        PruebasFirebase.insertar(this)
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

    /*
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.img_context_menu, menu)
    }

     */

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

    /*
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.imgMenu_opt1 -> {
                val generalDialog = if (userName != null){
                    GeneralDialog("Actual Username", userName!!, R.drawable.baseline_info_24)
                } else {
                    GeneralDialog("Error", "You are not login", R.drawable.baseline_error_24)
                }

                generalDialog.show(supportFragmentManager, "general01")
                true
            }

            R.id.imgMenu_opt2 -> {
                val actualDate = Calendar.getInstance().time
                GeneralDialog("Actual Date", DateFormat.getDateFormat(this).format(actualDate), R.drawable.baseline_calendar_today_24).show(supportFragmentManager, "general03")
                true
            }

            R.id.imgMenu_opt3 -> {
                GeneralDialog("About us", "We are the Team A :3", R.drawable.baseline_info_24).show(supportFragmentManager, "general04")
                true
            }

            else -> {super.onContextItemSelected(item)}
        }
    }

     */



}