package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_home.fcvWeather
import pl.pasiekaradosna.menadzerpasieki.databinding.FragmentHomeBinding
import pl.pasiekaradosna.menadzerpasieki.gui.WeatherFragment

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }

    override fun onStart() {
        super.onStart()

        fcvWeather.setOnClickListener {
            (childFragmentManager.fragments[0] as WeatherFragment)
            .getAndUpdateWeatherParameters()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}