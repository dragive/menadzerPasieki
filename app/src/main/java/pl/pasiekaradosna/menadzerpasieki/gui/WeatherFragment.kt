package pl.pasiekaradosna.menadzerpasieki.gui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_weather.ivIcon
import kotlinx.android.synthetic.main.fragment_weather.tvWeatherCloudsTextView
import kotlinx.android.synthetic.main.fragment_weather.tvWeatherTemperatureTextView
import kotlinx.android.synthetic.main.fragment_weather.tvWeatherWindTextView
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.classes.weather.WeatherData
import pl.pasiekaradosna.menadzerpasieki.classes.weather.WeatherService
import pl.pasiekaradosna.menadzerpasieki.services.Settings.AIR_TEMPERATURE_UNIT
import pl.pasiekaradosna.menadzerpasieki.services.Settings.CLOUD_AREA_UNIT
import pl.pasiekaradosna.menadzerpasieki.services.Settings.WIND_SPEED_UNIT

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment(), OnClickListener {


    override fun onStart() {
        super.onStart()

        WeatherService.getInstance()
            .getAndUpdateWeatherParameters("52.12257", "20.44369", this::updateView)

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

    @SuppressLint("SetTextI18n")
    fun updateView(weatherData: WeatherData) {
        this.tvWeatherCloudsTextView.text = "${weatherData.cloudAreaFraction} $CLOUD_AREA_UNIT"
        this.tvWeatherTemperatureTextView.text =
            "${weatherData.airTemperature} $AIR_TEMPERATURE_UNIT"
        this.tvWeatherWindTextView.text = "${weatherData.windSpeed} $WIND_SPEED_UNIT"
    }

    override fun onClick(v: View?) {
        WeatherService.getInstance()
            .getAndUpdateWeatherParameters("52.12257", "20.44369", this::updateView)
        //todo do zmiany
        Toast.makeText(context, "Updated weather widget", Toast.LENGTH_SHORT).show()
    }
}