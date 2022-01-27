package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.stream.Collectors
import kotlinx.android.synthetic.main.fragment_task_item.view.tvFragmentTaskItemDesc
import kotlinx.android.synthetic.main.fragment_task_item.view.tvFragmentTaskItemName
import kotlinx.android.synthetic.main.fragment_task_item.view.tvFragmentTaskItemNameOfHive
import pl.pasiekaradosna.menadzerpasieki.R.string
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper


class TaskAdapter(
    private var elements: List<TaskData>

) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private lateinit var instance: TaskAdapter

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(pl.pasiekaradosna.menadzerpasieki.R.layout.fragment_task_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val element = elements[position]

        holder.itemView.apply {
            this.setOnLongClickListener {
                ApiaryManagerDbHelper(context).deleteTask(element.id!!)
                elements = (ArrayList(elements).stream().filter{it.id != element.id!!} ).collect(
                    Collectors.toList())

                notifyDataSetChanged()
                Toast.makeText(context,context.getString(string.ToastCompletedTask),Toast.LENGTH_SHORT).show()

                true

            }



            tvFragmentTaskItemName.text = element.name
            tvFragmentTaskItemNameOfHive.text =
                ApiaryManagerDbHelper(context).getHiveById(element.hiveId)?.name
            tvFragmentTaskItemDesc.text = element.description


        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }
}