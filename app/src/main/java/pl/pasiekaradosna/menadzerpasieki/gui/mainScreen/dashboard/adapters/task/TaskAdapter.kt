package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_task_item.view.tvFragmentTaskItemName
import kotlinx.android.synthetic.main.fragment_task_item.view.tvFragmentTaskItemNameOfHive
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper

class TaskAdapter(
    private val elements: List<TaskData>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_task_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val element = elements[position]

        holder.itemView.apply {

//            if (ApiaryManagerDbHelper(context).getAllHives()?.stream()?.filter {
//                    it.id == element.hiveId
//                }?.count()!! > 0)

                tvFragmentTaskItemName.text = element.name
            tvFragmentTaskItemNameOfHive.text=  ApiaryManagerDbHelper(context).getHiveById(element.hiveId)?.name

        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }
}