package pl.pasiekaradosna.menadzerpasieki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ApiaryDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiary_details)

        actionBar?.setHomeButtonEnabled(true);
    }
}