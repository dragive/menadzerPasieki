package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_dashboard.fabCreateNewApiary
import pl.pasiekaradosna.menadzerpasieki.databinding.FragmentDashboardBinding
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.apiaryManagement.CreateApiaryActivity
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.apiary.ApiaryAdapter

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    private lateinit var apiaryAdapter: ApiaryAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        return root
    }

    override fun onStart() {
        super.onStart()
        fabCreateNewApiary.setOnClickListener { view ->
            val intent = Intent(context, CreateApiaryActivity::class.java)
            startActivity(intent)
        }
        //todo remake
//        val list = ApiaryManagerDbHelper(requireContext()).getAllApiaries()
//
//        apiaryAdapter = ApiaryAdapter(list!!)
//
//        rvDashboardMenu.adapter = apiaryAdapter
//
//        rvDashboardMenu.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}