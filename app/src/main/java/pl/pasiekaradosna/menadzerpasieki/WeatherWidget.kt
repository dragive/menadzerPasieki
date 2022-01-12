package pl.pasiekaradosna.menadzerpasieki

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class WeatherWidget : Fragment() {

    companion object {
        fun newInstance() = WeatherWidget()
    }

    private lateinit var viewModel: WeatherWidgetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_widget_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherWidgetViewModel::class.java]
        // TODO: Use the ViewModel
    }

}