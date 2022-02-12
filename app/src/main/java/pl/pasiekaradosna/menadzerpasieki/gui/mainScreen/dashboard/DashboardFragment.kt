package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_dashboard.fabCreateNewApiary
import kotlinx.android.synthetic.main.fragment_dashboard.rvDashboardMenu
import pl.pasiekaradosna.menadzerpasieki.databinding.FragmentDashboardBinding
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.apiaryManagement.CreateApiaryActivity
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.apiary.ApiaryAdapter
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper

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
        val list = ApiaryManagerDbHelper(requireContext()).getAllApiaries()

        apiaryAdapter = ApiaryAdapter(list!!)

        rvDashboardMenu.adapter = apiaryAdapter

        rvDashboardMenu.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}