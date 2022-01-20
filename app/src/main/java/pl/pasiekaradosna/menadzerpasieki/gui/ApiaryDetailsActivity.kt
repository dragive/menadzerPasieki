package pl.pasiekaradosna.menadzerpasieki.gui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.data.Settings

class ApiaryDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary_details)
        actionBar?.setHomeButtonEnabled(true)


        val id = intent?.getIntExtra("ApiaryId", -1)

        //warunek dostania się do pasieki, bez możliwości edycji bo nie ma ujemnych indeksów, więc
        // aktualna activity ma być zakończona
        if(id == -1){
            finish()
        }
    }

}