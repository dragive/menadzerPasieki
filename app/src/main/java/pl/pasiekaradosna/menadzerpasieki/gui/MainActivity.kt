package pl.pasiekaradosna.menadzerpasieki.gui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.bClearDb2
import kotlinx.android.synthetic.main.activity_main.bCreateDb2
import kotlinx.android.synthetic.main.activity_main.bLocationTest
import kotlinx.android.synthetic.main.activity_main.bMainApp
import kotlinx.android.synthetic.main.activity_main.bNextActivity
import kotlinx.android.synthetic.main.activity_main.rvMenu
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(Settings.TAG_APP, this.javaClass.name)
        setContentView(R.layout.activity_main)

        rvMenu.layoutManager = LinearLayoutManager(this)

        bNextActivity.setOnClickListener {
            val intent = Intent(this, ApiaryDetails2Activity::class.java)
            startActivity(intent)
        }

        bMainApp.setOnClickListener {
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
        }

        bLocationTest.setOnClickListener {
            val intent = Intent(this, LocationTestActivity::class.java)
            startActivity(intent)
        }

        bClearDb2.setOnClickListener {
            var bError = false
            try {
                ApiaryManagerDbHelper(this).dropTables()
            } catch (ex: Exception) {
                bError = true
                Toast.makeText(this, "Unable to droped Table", Toast.LENGTH_SHORT).show()
                Log.e(Settings.TAG_APP, "Unable to droped Table", ex)
            }
            if (!bError) {
                Toast.makeText(this, "Dropped", Toast.LENGTH_SHORT).show()
            }
        }

        bCreateDb2.setOnClickListener {
            var bError = false
            try {
                ApiaryManagerDbHelper(this).createTable()
            } catch (ex: Exception) {
                bError = true
                Toast.makeText(this, "Unable to Create", Toast.LENGTH_SHORT).show()
                Log.e(Settings.TAG_APP, "Unable to Create", ex)
            }
            if (!bError) {
                Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show()
            }
        }
        // do produkcji
//        val req = intent?.getIntExtra("LUNCH",-1)
//            if(req == null || req == -1){
//                finish()
//                startActivity(Intent(this,MainScreenActivity::class.java))
//            }
    }
}