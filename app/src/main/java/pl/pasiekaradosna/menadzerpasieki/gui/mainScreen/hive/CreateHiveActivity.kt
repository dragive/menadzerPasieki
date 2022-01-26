package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.hive

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_hive.bHiveAddSubmit
import kotlinx.android.synthetic.main.activity_create_hive.calvHiveAddQueenBirth
import kotlinx.android.synthetic.main.activity_create_hive.etHiveAddName
import kotlinx.android.synthetic.main.activity_create_hive.etHiveAddQueenBreed
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.hive.HiveData
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG

class CreateHiveActivity : AppCompatActivity() {
    private var hiveId: Int? = null
    private var apiaryId: Int? = null
    lateinit var hiveData :HiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_hive)


        hiveId = intent?.getIntExtra("HiveId", -1)!!
        apiaryId = intent?.getIntExtra("ApiaryId", -1)!!

        if(hiveId == null){
            hiveId = -1
        }
        if(hiveId == -1){
            setCreateListener()
        }
        else{
            val apiaryManagerDbHelper = ApiaryManagerDbHelper(this)

            hiveData = apiaryManagerDbHelper.getHiveById(hiveId!!)!!
            etHiveAddName.setText(hiveData.name)
            etHiveAddQueenBreed.setText(hiveData.queenBreed)
            etHiveAddQueenBreed.setText(hiveData.queenBirthDate)
            setUpdateListener()
        }
    }

    private fun setCreateListener(){

        bHiveAddSubmit.setOnClickListener {
            val name: String = etHiveAddName.text.toString()
            val breed: String = etHiveAddQueenBreed.text.toString()

            val date: String = calvHiveAddQueenBirth.date.toString()

            val hive = HiveData(-1, name, apiaryId!!, breed, date)

            if(ApiaryManagerDbHelper(this).insertHive(hive)){
                Toast.makeText(this,"Hive was added", Toast.LENGTH_SHORT).show()
                Log.d(Settings.TAG,"true")
                finish()
            }
            else{
                Toast.makeText(this,"Hive was NOT added!", Toast.LENGTH_SHORT).show()
                Log.d(Settings.TAG,"false")

            }

        }
    }


    private fun setUpdateListener(){

        Log.d(TAG,"apiaryId"+apiaryId)
        Log.d(TAG,"hiveId"+hiveId)

        bHiveAddSubmit.setOnClickListener {
            val name: String = etHiveAddName.text.toString()
            val breed: String = etHiveAddQueenBreed.text.toString()

            val date: String = calvHiveAddQueenBirth.date.toString()

            val hive = HiveData(hiveId, name, apiaryId!!, breed, date)

            ApiaryManagerDbHelper(this).updateHive(hive)
            finish()

        }
    }


}