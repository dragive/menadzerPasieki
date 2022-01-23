package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.hive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_hive_summary.view.tvHiveListItemBreed
import pl.pasiekaradosna.menadzerpasieki.R

class HiveAdapter(
    private val elements: List<HiveData>
) : RecyclerView.Adapter<HiveAdapter.HiveViewHolder>() {
    class HiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiveViewHolder {
        return HiveViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_hive_summary, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HiveViewHolder, position: Int) {
        val element = elements[position]

        holder.itemView.apply {
            this.tvHiveListItemBreed.text = element.name
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }


}