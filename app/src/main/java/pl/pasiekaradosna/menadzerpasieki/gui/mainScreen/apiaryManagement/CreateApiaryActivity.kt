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
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.R.string
import pl.pasiekaradosna.menadzerpasieki.classes.data.ApiaryData
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings

class CreateApiaryActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationRequestCode = 1000

    private var latitude = 0.0
    private var longitude = 0.0

    private var cancellationTokenSource = CancellationTokenSource()

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                locationRequestCode
            )
            return
        }


    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        bGetLocation.setOnClickListener {
            try {
                pullLocation()

            } catch (ex: Exception) {
                Log.i(Settings.TAG_APP, "Unable to get location")
                Toast.makeText(this, "Unable to get Location!", Toast.LENGTH_SHORT).show()
            }
        }


        // Ustawienie listener??w
        if (apiaryId == -1) {

            setCreateListener()
        } else {

            setValuesToView()

            setUpdateListener()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(Settings.TAG_APP, "s " + object : Any() {}.javaClass.enclosingMethod.name)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(Settings.TAG_APP, "" + object : Any() {}.javaClass.enclosingMethod.name)
        if (requestCode == this.locationRequestCode) {

            // If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(Settings.TAG_APP, "pull" + object : Any() {}.javaClass.enclosingMethod.name)
                pullLocation()
            } else {
                Log.d(Settings.TAG_APP, "toast" + object : Any() {}.javaClass.enclosingMethod.name)
                Toast.makeText(this, getString(string.ToastPermissionDenied), Toast.LENGTH_SHORT)
                    .show();
            }


        }
    }


    private fun pullLocation(): List<String>? {
//        Log.i(
//            Settings.TAG,
//            "true " + (getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
//                LocationManager.GPS_PROVIDER
//            )
//        )
        if ((getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )
        ) {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return null
            }
            // Main code
            val currentLocationTask: Task<Location> = fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )

            currentLocationTask.addOnSuccessListener(this, fun(location) {
                if (location != null) {
                    this.latitude = location.latitude;
                    this.longitude = location.longitude;

                    Log.i(Settings.TAG_APP, "Longitude: $longitude, Latitude: $latitude")
//                    tvLocation.text = "$latitude $longitude"
                    etLocationCoordinates.setText("$latitude, $longitude")

                } else {
                    Log.i(Settings.TAG_APP, "Lokalizacja to null")
                }
            })
        } else {
            Toast.makeText(this, "Trun on GPS!", Toast.LENGTH_SHORT).show()//todo
        }

        return listOf(this.latitude.toString(), this.longitude.toString())
    }


    private fun setCreateListener() {
        bApiaryCreateSubmit.setOnClickListener {
            val name = etApiaryCreationName.text.toString()
            val coords = etLocationCoordinates.text.toString()
            val date =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            if (name == "") {
                Log.i(Settings.TAG_APP, Date().toString())
                Toast.makeText(
                    this,
                    getString(string.ToastEmptyNameOfApiary),
                    Toast.LENGTH_SHORT
                ) //todo
                    .show()
                return@setOnClickListener
            }

            //todo remake ApiaryManagerDbHelper(this).createApiary(ApiaryData(null, name, date, coords))
            finish()
        }
    }


    private fun setUpdateListener() {
        bApiaryCreateSubmit.setOnClickListener {
            val name = etApiaryCreationName.text.toString()
            val coords = etLocationCoordinates.text.toString()
            val date =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            if (name == "") {
                Log.i(Settings.TAG_APP, Date().toString())
                Toast.makeText(this, getString(string.ToastEmptyNameOfApiary), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            //todo remake ApiaryManagerDbHelper(this).updateApiary(ApiaryData(apiaryId, name, date, coords))
            finish()
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