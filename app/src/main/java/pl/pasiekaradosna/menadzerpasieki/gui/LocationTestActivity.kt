package pl.pasiekaradosna.menadzerpasieki.gui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_location_test.*
import pl.pasiekaradosna.menadzerpasieki.services.Settings
import java.lang.Exception
import android.location.LocationManager
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import pl.pasiekaradosna.menadzerpasieki.R


class LocationTestActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationRequestCode = 1000

    private var latitude = 0.0
    private var longitude = 0.0

    private var cancellationTokenSource = CancellationTokenSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_test)
        Log.d(Settings.TAG,""+object : Any() {}.javaClass.enclosingMethod.name)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        bGetLocation.setOnClickListener {
            try {Log.d(Settings.TAG,"onclick "+object : Any() {}.javaClass.enclosingMethod.name)
                pullLocation()
            } catch (ex: Exception) {
                Log.e(Settings.TAG, "error", ex)
            }
        }


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(Settings.TAG,"if begin"+object : Any() {}.javaClass.enclosingMethod.name)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(Settings.TAG,"s "+object : Any() {}.javaClass.enclosingMethod.name)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(Settings.TAG,""+object : Any() {}.javaClass.enclosingMethod.name)
        if (requestCode == this.locationRequestCode) {

            // If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(Settings.TAG,"pull"+object : Any() {}.javaClass.enclosingMethod.name)
                pullLocation()
            } else {
                Log.d(Settings.TAG,"toast"+object : Any() {}.javaClass.enclosingMethod.name)
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private fun pullLocation() {
        Log.i(Settings.TAG,"true "+(getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
            LocationManager.GPS_PROVIDER
        ))
        if ((getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )
        )
        {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            // Main code
            val currentLocationTask: Task<Location> = fusedLocationClient.getCurrentLocation(
                PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )

            currentLocationTask.addOnSuccessListener(this, fun(location) {
                if(location!=null) {
                    this.latitude = location.latitude;
                    this.longitude = location.longitude;

                    Log.i(Settings.TAG, "Longitude: $longitude, Latitude: $latitude")
                    tvLocation.text = "$latitude $longitude"

                }
                else{
                    Log.i(Settings.TAG,"Lokalizacja to null")
                }
            })
        }
        else{
            Toast.makeText(this,"Uruchom Lokalizację!",Toast.LENGTH_SHORT).show()
        }
    }

}
