package pl.pasiekaradosna.menadzerpasieki.ui.adapters.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.pasiekaradosna.menadzerpasieki.R
import android.util.Log
import kotlinx.android.synthetic.main.apiary_item.view.*
import pl.pasiekaradosna.menadzerpasieki.dal.Settings.TAG

class ApiaryAdapter (
    private val elements: List<Apiary>
        ) : RecyclerView.Adapter<ApiaryAdapter.ApiaryViewHolder>(){


        class ApiaryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiaryViewHolder {
        Log.d(TAG,"onCreateViewHolder")
        return ApiaryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.apiary_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ApiaryViewHolder, position: Int) {
        val element = elements[position]
        Log.d(TAG,"onBindViewHolder")
        holder.itemView.apply {
            tvApiaryName.text = element.name
            Log.d(TAG, element.name!!)
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG,"getItemCount: ${elements.size}")
        return elements.size
    }
}