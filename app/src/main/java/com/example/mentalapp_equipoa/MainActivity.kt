package com.example.mentalapp_equipoa

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.mentalapp_equipoa.dialogs.ChangeUserDialog
import com.example.mentalapp_equipoa.dialogs.GeneralDialog
import com.example.mentalapp_equipoa.dialogs.PreviousDialog
import com.example.mentalapp_equipoa.dialogs.TestDialog

val previous_results = ArrayList<String>()
var userName: String? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.topAppBar))
        registerForContextMenu(findViewById(R.id.imgMentapp))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.optionsmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.optionsOpt1 -> {
                ChangeUserDialog().show(supportFragmentManager, "userDialog1")
                true
            }

            R.id.optionsOpt2 -> {
                GeneralDialog("Advices", "Drink a lot of water :3", R.drawable.baseline_info_24).show(supportFragmentManager, "general05")
                true
            }

            R.id.optionsOpt3 -> {
                GeneralDialog("Information", "This app sells all your data to the chinese", R.drawable.baseline_info_24).show(supportFragmentManager, "general05")
                true
            }

            else -> {
                Toast.makeText(this, "Not yet implemented", Toast.LENGTH_LONG).show()
                true
            }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.img_context_menu, menu)
    }

    fun btnTestOnClick(view: View) {
        val dialog = TestDialog()
        dialog.show(supportFragmentManager, "TestDialog")
    }

    fun btnPreviousOnClick(view: View) {
        val dialog = PreviousDialog()
        dialog.show(supportFragmentManager, "PreviousDialog")
    }

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
}