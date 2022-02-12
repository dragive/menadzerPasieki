package pl.pasiekaradosna.menadzerpasieki.gui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.pasiekaradosna.menadzerpasieki.R

class ApiaryDetails2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary_details_2)

        actionBar?.setHomeButtonEnabled(true);
    }
}