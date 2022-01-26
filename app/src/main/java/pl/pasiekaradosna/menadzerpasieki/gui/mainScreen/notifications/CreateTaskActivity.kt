package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.notifications

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_task.bTaskCreateSubmit
import kotlinx.android.synthetic.main.activity_create_task.edTaskCreateDescription
import kotlinx.android.synthetic.main.activity_create_task.edTaskCreateName
import pl.pasiekaradosna.menadzerpasieki.R.layout
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.task.TaskData
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG

class CreateTaskActivity : AppCompatActivity() {

    private var hiveId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_create_task)

        hiveId = intent?.getIntExtra("HiveId",-1)!!


    }

    override fun onStart() {
        super.onStart()

        bTaskCreateSubmit.setOnClickListener {
            val taskd = TaskData(
                null,
                this.edTaskCreateName.text.toString(),
                edTaskCreateDescription.text.toString(),
                hiveId
            )
            val result = ApiaryManagerDbHelper(this).insertTask(
                taskd
            )
            Log.d(TAG, "" + result)
            finish()
        }
    }
}