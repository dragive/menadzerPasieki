package pl.pasiekaradosna.menadzerpasieki.gui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.pasiekaradosna.menadzerpasieki.R

class ApiaryDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary_details)
        actionBar?.setHomeButtonEnabled(true)
    }
}