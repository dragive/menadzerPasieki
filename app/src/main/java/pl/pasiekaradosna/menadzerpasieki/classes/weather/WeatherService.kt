package pl.pasiekaradosna.menadzerpasieki.classes.weather

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import pl.pasiekaradosna.menadzerpasieki.services.Settings.*
import java.io.IOException
import kotlin.reflect.KFunction1

class WeatherService private constructor() {

    private val client = OkHttpClient()

    companion object {
        private var instance: WeatherService? = null;

        fun getInstance(): WeatherService {
            if (instance == null) {
                instance = WeatherService()
            }
            return instance!!
        }
    }

    fun getAndUpdateWeatherParameters(
        lat: String,
        lon: String,
        updateView: KFunction1<WeatherData, Unit>
    ) {
        Log.d(TAG_APP, "getAndUpdateWeatherParameters")
        run(
            "https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=${lat}&lon=${lon}",
            updateView
        )
        // todo dodanie poprawnej pogody
    }

    private fun run(url: String, updateView: (WeatherData) -> Unit) {
        val request = Request.Builder()
            .addHeader("User-Agent", "pasiekaradosna.pl pasiekaradosna@gmail.com")
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG_APP, "OnFailure call in weatherFragment", e)
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
}