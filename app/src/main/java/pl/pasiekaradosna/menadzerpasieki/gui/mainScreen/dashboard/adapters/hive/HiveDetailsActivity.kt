package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.hive

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hive_details.bActivityHiveDetailsDelete
import kotlinx.android.synthetic.main.activity_hive_details.tvHiveDetailsApiaryName
import kotlinx.android.synthetic.main.activity_hive_details.tvHiveDetailsName
import kotlinx.android.synthetic.main.activity_hive_details.tvHiveDetailsQueenBreed
import kotlinx.android.synthetic.main.activity_hive_details.tvHiveDetailsQueenDateOfBirth
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.apiary.ApiaryData
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG

class HiveDetailsActivity : AppCompatActivity() {
    private var hiveData: HiveData? = null
    private var apiaryData: ApiaryData? = null
    private var apiaryManagerDbHelper: ApiaryManagerDbHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hive_details)

        val hiveId: Int = intent?.getIntExtra("HiveId",-1)!!

        Log.i(TAG,"HiveId in HiveDetailsActivity $hiveId")
        apiaryManagerDbHelper=  ApiaryManagerDbHelper(this)
        hiveData = apiaryManagerDbHelper!!.getHiveById(hiveId)

        if(hiveData!= null && hiveData?.apiaryId!=null){
            apiaryData = apiaryManagerDbHelper!!.getApiaryById(hiveData?.apiaryId!!)
        }

    }

    override fun onStart() {
        super.onStart()

        if(hiveData == null || apiaryData == null){
            finish();
        }

        var hiveDataParsed :HiveData = hiveData!!

        tvHiveDetailsName.text = hiveDataParsed.name
        tvHiveDetailsApiaryName.text = apiaryData?.name
        tvHiveDetailsQueenBreed.text = hiveDataParsed.queenBreed
        tvHiveDetailsQueenDateOfBirth.text = hiveDataParsed.queenBirthDate


        bActivityHiveDetailsDelete.setOnClickListener {
            this.apiaryManagerDbHelper!!.deleteHive(hiveDataParsed.id!!)

            finish()
        }
    }
}