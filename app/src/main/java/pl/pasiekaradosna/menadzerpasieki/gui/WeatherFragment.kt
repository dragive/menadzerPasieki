package pl.pasiekaradosna.menadzerpasieki.gui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.IOException
import kotlinx.android.synthetic.main.fragment_weather.ivIcon
import kotlinx.android.synthetic.main.fragment_weather.tvWeatherCloudsTextView
import kotlinx.android.synthetic.main.fragment_weather.tvWeatherTemperatureTextView
import kotlinx.android.synthetic.main.fragment_weather.tvWeatherWindTextView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.classes.weather.WeatherData
import pl.pasiekaradosna.menadzerpasieki.services.Settings.AIR_TEMPERATURE_UNIT
import pl.pasiekaradosna.menadzerpasieki.services.Settings.CLOUD_AREA_UNIT
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG_APP
import pl.pasiekaradosna.menadzerpasieki.services.Settings.WIND_SPEED_UNIT

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment(), OnClickListener {
    private var param1: String? = null
    private var param2: String? = null

    private val client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onStart() {
        super.onStart()

        getAndUpdateWeatherParameters()


        ivIcon.setOnLongClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("LUNCH", 0)
            startActivity(intent)
            true
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //todo przeniesienie tego do interfejsu i klasy
    fun getAndUpdateWeatherParameters() {
        Log.d(TAG_APP, "getAndUpdateWeatherParameters")
        run("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=52.12257&lon=20.44369")
    }

    private fun run(url: String) {
        val request = Request.Builder()
            .addHeader("User-Agent", "pasiekaradosna.pl pasiekaradosna@gmail.com")
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG_APP, "OnFailure run in weatherFragment", e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val rawJson: String = response.body()?.string().toString()
                    val json = JSONObject(rawJson)
                    val weatherData = WeatherData.parseFromJson(json)
                    updateView(weatherData)
                } catch (err: Exception) {
                    Log.e(TAG_APP, "Error while Calling Api", err)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateView(weatherData: WeatherData) {
        this.tvWeatherCloudsTextView.text = "${weatherData.cloudAreaFraction} $CLOUD_AREA_UNIT"
        this.tvWeatherTemperatureTextView.text =
            "${weatherData.airTemperature} $AIR_TEMPERATURE_UNIT"
        this.tvWeatherWindTextView.text = "${weatherData.windSpeed} $WIND_SPEED_UNIT"
    }

    override fun onClick(v: View?) {
        getAndUpdateWeatherParameters()
        Toast.makeText(context, "Updated weather widget", Toast.LENGTH_SHORT).show()
    }
}