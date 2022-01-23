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
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG

class CreateHiveActivity : AppCompatActivity() {
    private var apiaryId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_hive)


        apiaryId = intent?.getIntExtra("ApiaryId", -1)!!

        if (apiaryId == null || apiaryId == -1) {
            finish()
        }
        calvHiveAddQueenBirth

        bHiveAddSubmit.setOnClickListener {
            val name: String = etHiveAddName.text.toString()
            val breed: String = etHiveAddQueenBreed.text.toString()

            val date: String = calvHiveAddQueenBirth.date.toString()

            val hive = HiveData(-1, name, apiaryId!!, breed, date)

            if(ApiaryManagerDbHelper(this).insertHive(hive)){
                Toast.makeText(this,"Hive was added",Toast.LENGTH_SHORT).show()
                Log.d(TAG,"true")
            }
            else{
                Toast.makeText(this,"Hive was NOT added!",Toast.LENGTH_SHORT).show()
                Log.d(TAG,"false")

            }

        }
    }


}