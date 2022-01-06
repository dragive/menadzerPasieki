package pl.pasiekaradosna.menadzerpasieki.ui.dashboard.apiaryManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.pasiekaradosna.menadzerpasieki.R

class CreateApiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_apiary)

        actionBar?.setHomeButtonEnabled(true);
    }

}