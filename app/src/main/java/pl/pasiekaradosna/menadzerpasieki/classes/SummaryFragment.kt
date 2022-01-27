package pl.pasiekaradosna.menadzerpasieki.classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_summary.tvSummaryApiaries
import kotlinx.android.synthetic.main.fragment_summary.tvSummaryHives
import kotlinx.android.synthetic.main.fragment_summary.tvSummaryTasks
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper

class SummaryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false)


    }

    override fun onStart() {
        super.onStart()


        val apiaryManagerDbHelper = ApiaryManagerDbHelper(requireContext())

        val hives:Int = apiaryManagerDbHelper.countAllHives()
        val apiaries:Int = apiaryManagerDbHelper.countApiaries()
        val tasks:Int = apiaryManagerDbHelper.countTasks()


        this.tvSummaryApiaries.text = apiaries.toString()
        this.tvSummaryHives.text = hives.toString()
        this.tvSummaryTasks.text = tasks.toString()


    }
}