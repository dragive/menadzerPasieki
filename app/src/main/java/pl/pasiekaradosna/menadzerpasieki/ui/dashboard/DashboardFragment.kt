package pl.pasiekaradosna.menadzerpasieki.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_dashboard.*
import pl.pasiekaradosna.menadzerpasieki.ApiaryDetails
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.WeatherFragment
import pl.pasiekaradosna.menadzerpasieki.dal.Settings
import pl.pasiekaradosna.menadzerpasieki.databinding.FragmentDashboardBinding
import pl.pasiekaradosna.menadzerpasieki.ui.dashboard.apiaryManagement.CreateApiaryActivity

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textView2
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })

//        Toast.makeText(context,""+activity,Toast.LENGTH_SHORT).show()
//        Log.d(Settings.TAG, "activity: $activity")
//        Log.d(Settings.TAG, "context: $context")



        /*{
            Log.d(Settings.TAG,""+activity)
            val intent = Intent(context, CreateApiaryActivity::class.java)
            context?.startActivity(intent)
        }
*/
        return root
    }

    override fun onStart() {
        super.onStart()
        fabCreateNewApiary.setOnClickListener{ view ->
            val intent = Intent(context,CreateApiaryActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}