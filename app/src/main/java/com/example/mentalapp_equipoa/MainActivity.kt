package com.example.mentalapp_equipoa

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mentalapp_equipoa.dialogs.ChangeUserDialog
import com.example.mentalapp_equipoa.dialogs.PreviousDialog
import com.example.mentalapp_equipoa.dialogs.TestDialog

val previous_results = ArrayList<String>()
var userName: String? = null
private const val TAG = "MainActivity"
const val EXTRA_MESSAGE = "com.example.mentalapp_equipoa.MESSAGE"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Ya no se usa
        setSupportActionBar(findViewById(R.id.topAppBar))
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

    fun btnTestOnClick(view: View) {
        TestDialog(getString(R.string.information_test_dialog),
            getString(R.string.it_works), R.drawable.baseline_info_24).show(supportFragmentManager, "test01")
    }

    fun btnPreviousOnClick(view: View) {
        val dialog = PreviousDialog()
        dialog.show(supportFragmentManager, "PreviousDialog")
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


    fun asignarVariablesCalcularNota(genero: String, edad: Int, factor: Int): Pair<Double, Double> {
        var x = 0.0
        var y = 0.0

        if(genero=="mujer"){
            if(factor==1){
                if(edad<=14){
                    x=19.2
                    y=30.0
                }
                if(edad<=16){
                    x=26.4
                    y=32.4
                }
                if(edad<=18){
                    x=28.2
                    y=38.4
                }
            }
            if(factor==2){
               if(edad<=14){
                    x=0.0
                    y=1.0
                }
                if(edad<=16){
                    x=0.0
                    y=2.0
                }
                if(edad<=18){
                    x=0.0
                    y=1.0
                }
            }
            if(factor==3){
                if(edad<=14){
                    x=26.0
                    y=34.1
                }
                if(edad<=16){
                    x=28.0
                    y=36.0
                }
                if(edad<=18){
                    x=30.0
                    y=35.0
                }
            }
        }
        if(genero=="hombre"){
            if(factor==1){
                if(edad<=14){
                    x=13.2
                    y=20.4
                }
                if(edad<=16){
                    x=14.4
                    y=21.6
                }
                if(edad<=18){
                    x=14.4
                    y=22.0
                }
            }
            if(factor==2){
               if(edad<=14){
                    x=0.0
                    y=1.0
                }
                if(edad<=16){
                    x=0.0
                    y=2.0
                }
                if(edad<=18){
                    x=0.0
                    y=2.5
                }
            }
            if(factor==3){
                if(edad<=14){
                    x=19.0
                    y=28.0
                }
                if(edad<=16){
                    x=19.0
                    y=26.0
                }
                if(edad<=18){
                    x=21.0
                    y=26.0
                }
            }
        }
        if(genero=="nb"){
            if(factor==1){
                if(edad<=14){
                    x=16.2
                    y=25.2
                }
                if(edad<=16){
                    x=20.4
                    y=32.4
                }
                if(edad<=18){
                    x=21.3
                    y=30.2
                }
            }
            if(factor==2){
               if(edad<=14){
                    x=0.0
                    y=1.0
                }
                if(edad<=16){
                    x=0.0
                    y=2.0
                }
                if(edad<=18){
                    x=0.0
                    y=1.0
                }
            }
            if(factor==3){
                if(edad<=14){
                    x=22.5
                    y=27.0
                }
                if(edad<=16){
                    x=23.5
                    y=31.0
                }
                if(edad<=18){
                    x=25.5
                    y=30.5
                }
            }
        }
        return Pair(x, y)
    }

    /*
    Esta funcion sera cambiada una vez se introduzca la bbd
     */
    /*
    fun calcularNota(){
        var genero = ""
        var edad = 0
        var sumFactor1 = 0
        var sumFactor2 = 0
        var sumFactor3 = 0

        var x = 0.0
        var y = 0.0

        var j = 0
        for(i in 0..factor.size){
            if(i==1){
                sumFactor1+=respuestas[j]
            }
            if(i==2){
                sumFactor2+=respuestas[j]
            }
            if(i==3){
                sumFactor3+=respuestas[j]
            }
            j++
        }

        var sumFactores =  arrayOf<Int>(sumFactor1, sumFactor2, sumFactor3)
        var nivel = arrayOf<String>("","","")

        var t = 0

        for(i in 0..3){
            var variables = asignarVariablesCalcularNota(genero, edad, i)
            if(sumFactores[t]>=x){
                nivel[t] = "bajo"
            }
            if(sumFactores[t]>x || sumFactores[t]<=y){
                nivel[t] = "medio"
            }
            if(sumFactores[t]<y){
                nivel[t] = "alto"
            }
        }
    }
    */
}