package pl.pasiekaradosna.menadzerpasieki.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.pasiekaradosna.menadzerpasieki.R

class MockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mock)

    }

    override fun onStart() {
        super.onStart()

        finish()
    }
}