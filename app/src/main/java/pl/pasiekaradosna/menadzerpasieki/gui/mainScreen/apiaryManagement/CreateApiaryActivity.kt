package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.apiaryManagement

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlinx.android.synthetic.main.activity_create_apiary.*
import kotlinx.android.synthetic.main.activity_location_test.bGetLocation
import pl.pasiekaradosna.menadzerpasieki.CustomLocationAppCompatActivity
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.R.string
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.apiary.ApiaryData
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings
import pl.pasiekaradosna.menadzerpasieki.services.Settings.*

class CreateApiaryActivity : CustomLocationAppCompatActivity() {

    private var apiaryId: Int = -1
    private lateinit var apiaryData: ApiaryData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_apiary)

        actionBar?.setHomeButtonEnabled(true);

        var apiaryIdWithNull = intent?.getIntExtra("ApiaryId", -1)


        if (apiaryIdWithNull == null) {
            apiaryIdWithNull = -1;
            apiaryId = -1
        }

        if (apiaryIdWithNull != -1) {
            apiaryData = ApiaryManagerDbHelper(this).getApiaryById(apiaryIdWithNull)!!
            apiaryId = apiaryIdWithNull
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        bGetLocation.setOnClickListener {
            try {
                pullLocation()
            } catch (ex: Exception) {
                Log.i(TAG_APP, "Unable to get location")
                Toast.makeText(this, "Unable to get Location!", Toast.LENGTH_SHORT).show()
            }
        }


        // Ustawienie listenerów
        if (apiaryId == -1) {

            setCreateListener()
        } else {

            setValuesToView()

            setUpdateListener()
        }
    }

    private fun setCreateListener() {
        bApiaryCreateSubmit.setOnClickListener {
            val name = etApiaryCreationName.text.toString()
            val cords = etLocationCoordinates.text.toString()
            val date =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            if (name == "") {
                Log.i(TAG_APP, Date().toString())
                Toast.makeText(
                    this,
                    getString(string.ToastEmptyNameOfApiary),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                ApiaryManagerDbHelper(this).createApiary(ApiaryData(null, name, date, cords))
                finish()
            }
        }
    }


    private fun setUpdateListener() {
        bApiaryCreateSubmit.setOnClickListener {
            val name = etApiaryCreationName.text.toString()
            val coords = etLocationCoordinates.text.toString()
            val date =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            if (name == "") {
                Log.i(TAG_APP, Date().toString())
                Toast.makeText(this, getString(string.ToastEmptyNameOfApiary), Toast.LENGTH_SHORT)
                    .show()
            } else {

                ApiaryManagerDbHelper(this).updateApiary(ApiaryData(apiaryId, name, date, coords))
                finish()
            }
        }
    }

    private fun setValuesToView() {
        etApiaryCreationName.setText(apiaryData.name)
        etLocationCoordinates.setText(apiaryData.location)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}