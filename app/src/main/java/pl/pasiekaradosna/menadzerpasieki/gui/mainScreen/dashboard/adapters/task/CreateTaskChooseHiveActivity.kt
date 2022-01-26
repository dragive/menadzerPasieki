package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.task

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.pasiekaradosna.menadzerpasieki.R

class CreateTaskChooseHiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task_choose_hive)
    }

    override fun onStart() {
        super.onStart()
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }
}