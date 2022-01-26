package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.pasiekaradosna.menadzerpasieki.R.layout

class TaskItemFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(layout.fragment_task_item, container, false)
    }

}