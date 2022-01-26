package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.hive

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_hive_summary.view.tvHiveListItemBreed
import kotlinx.android.synthetic.main.fragment_hive_summary.view.tvHiveListItemName
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.services.Settings
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG

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
            Log.d(TAG,"onBindViewHolder "+elements)
        holder.itemView.apply {
            this.tvHiveListItemBreed.text = element.queenBreed
            this.tvHiveListItemName.text = element.name

            this.setOnClickListener {
                try{val intent = Intent(context, HiveDetailsActivity::class.java)
                    val bundle = Bundle()
                    bundle.putInt("HiveId", elements[position].id!!)
                    intent.putExtras(bundle)
                    context.startActivity(intent)}
                catch (err:Exception){
                    Log.e(Settings.TAG,"ERROR while Hive activity details", err)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }


}