package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_notifications.rcTaskListView
import pl.pasiekaradosna.menadzerpasieki.databinding.FragmentNotificationsBinding
import pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.task.TaskAdapter
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG_APP

class NotificationsFragment : Fragment() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val list = ApiaryManagerDbHelper(requireContext()).getAllTasks()
        taskAdapter = TaskAdapter(list!!)

        rcTaskListView.adapter = taskAdapter

        rcTaskListView.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}