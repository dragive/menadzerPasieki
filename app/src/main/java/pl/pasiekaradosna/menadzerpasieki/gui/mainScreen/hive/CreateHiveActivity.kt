package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.hive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_hive.calvHiveAddQueenBirth
import pl.pasiekaradosna.menadzerpasieki.R

class CreateHiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_hive)


        calvHiveAddQueenBirth.setOnDateChangeListener { view, year, month, dayOfMonth -> {

        } }


    }


}