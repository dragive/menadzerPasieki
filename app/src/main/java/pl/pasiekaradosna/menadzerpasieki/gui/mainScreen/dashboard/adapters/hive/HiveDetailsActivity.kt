package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.hive

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hive_details.bActivityHiveDetailsAddTask
import kotlinx.android.synthetic.main.activity_hive_details.bActivityHiveDetailsDelete
import kotlinx.android.synthetic.main.activity_hive_details.bActivityHiveDetailsEdit
import kotlinx.android.synthetic.main.activity_hive_details.tvHiveDetailsApiaryName
import kotlinx.android.synthetic.main.activity_hive_details.tvHiveDetailsName
import kotlinx.android.synthetic.main.activity_hive_details.tvHiveDetailsQueenBreed
import kotlinx.android.synthetic.main.activity_hive_details.tvHiveDetailsQueenDateOfBirth
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.apiary.ApiaryData
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.hive.CreateHiveActivity
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.notifications.CreateTaskActivity
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG_APP

class HiveDetailsActivity : AppCompatActivity() {
    private var hiveData: HiveData? = null
    private var apiaryData: ApiaryData? = null
    private var apiaryManagerDbHelper: ApiaryManagerDbHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hive_details)

        actionBar?.setHomeButtonEnabled(true)

        val hiveId: Int = intent?.getIntExtra("HiveId", -1)!!

        apiaryManagerDbHelper = ApiaryManagerDbHelper(this)
        hiveData = apiaryManagerDbHelper!!.getHiveById(hiveId)

        if (hiveData != null && hiveData?.apiaryId != null) {
            apiaryData = apiaryManagerDbHelper!!.getApiaryById(hiveData?.apiaryId!!)
        }

    }

    override fun onStart() {
        super.onStart()

        if (hiveData == null || apiaryData == null) {
            finish();
        }

        val hiveDataParsed: HiveData = hiveData!!

        tvHiveDetailsName.text = hiveDataParsed.name
        tvHiveDetailsApiaryName.text = apiaryData?.name
        tvHiveDetailsQueenBreed.text = hiveDataParsed.queenBreed
        tvHiveDetailsQueenDateOfBirth.text = hiveDataParsed.queenBirthDate


        bActivityHiveDetailsDelete.setOnClickListener {
            this.apiaryManagerDbHelper!!.deleteHive(hiveDataParsed.id!!)

            finish()
        }

        bActivityHiveDetailsEdit.setOnClickListener {
            val intent = Intent(this, CreateHiveActivity::class.java)
            intent.putExtra("ApiaryId", apiaryData!!.id)
            intent.putExtra("HiveId", hiveData!!.id)

            startActivity(intent)
            finish()
        }

        bActivityHiveDetailsAddTask.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            intent.putExtra("HiveId", hiveData!!.id)
            startActivity(intent)
        }
    }
}