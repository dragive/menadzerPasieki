package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.hive

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlinx.android.synthetic.main.activity_create_hive.bHiveAddSubmit
import kotlinx.android.synthetic.main.activity_create_hive.calvHiveAddQueenBirth
import kotlinx.android.synthetic.main.activity_create_hive.etHiveAddName
import kotlinx.android.synthetic.main.activity_create_hive.etHiveAddQueenBreed
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.classes.data.HiveData
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG_APP

class CreateHiveActivity : AppCompatActivity() {
    private var hiveId: Int? = null
    private var apiaryId: Int? = null
    lateinit var hiveData: HiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_hive)

        hiveId = intent?.getIntExtra("HiveId", -1)!!
        apiaryId = intent?.getIntExtra("ApiaryId", -1)!!

        if (hiveId == null || hiveId == -1) {
            hiveId = -1;
            setCreateListener()
        } else {
            val apiaryManagerDbHelper = ApiaryManagerDbHelper(this)

            hiveData = apiaryManagerDbHelper.getHiveById(hiveId!!)!!
//            todo remake
//            etHiveAddName.setText(hiveData.name)
//            etHiveAddQueenBreed.setText(hiveData.queenBreed)
            setUpdateListener()
        }
    }

    private fun setCreateListener() {

        bHiveAddSubmit.setOnClickListener {
            val name: String = etHiveAddName.text.toString()
            val breed: String = etHiveAddQueenBreed.text.toString()

            val date: String = getDate(calvHiveAddQueenBirth.date, "yyyy MM dd")!!

            //todo remake val hive = HiveData(-1, name, apiaryId!!, breed, date)

//            todo remake
            //            if (ApiaryManagerDbHelper(this).insertHive(hive)) {
//                Toast.makeText(
//                    this,
//                    getString(string.ToastHiveWasAddedSuccessfully),
//                    Toast.LENGTH_SHORT
//                ).show()
//                Log.d(TAG_APP, "true")
//                finish()
//            } else {
//                Toast.makeText(this, getString(string.ToastAddingTaskFailed), Toast.LENGTH_SHORT)
//                    .show()
//                Log.d(TAG_APP, "false")
//
//            }

        }
    }

    private fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    private fun getMilis(date: String): Long {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(
            "yyyy MM dd", Locale.ROOT
        )
        return OffsetDateTime.parse(date, formatter)
            .toInstant()
            .toEpochMilli()
    }


    private fun setUpdateListener() {

        Log.d(TAG_APP, "apiaryId" + apiaryId)
        Log.d(TAG_APP, "hiveId" + hiveId)

        bHiveAddSubmit.setOnClickListener {
            val name: String = etHiveAddName.text.toString()
            val breed: String = etHiveAddQueenBreed.text.toString()

            val date: String = getDate(calvHiveAddQueenBirth.date, "yyyy MM dd")!!

            //todo remake val hive = HiveData(hiveId, name, apiaryId!!, breed, date)

            //todo remake ApiaryManagerDbHelper(this).updateHive(hive)
            finish()

        }
    }


}