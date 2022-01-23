package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.apiary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.pasiekaradosna.menadzerpasieki.R
import kotlinx.android.synthetic.main.fragment_apiary_item.view.*
import pl.pasiekaradosna.menadzerpasieki.gui.ApiaryDetailsActivity

class ApiaryAdapter (
    private val elements: List<ApiaryData>
        ) : RecyclerView.Adapter<ApiaryAdapter.ApiaryViewHolder>(){


        class ApiaryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiaryViewHolder {
        return ApiaryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_apiary_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ApiaryViewHolder, position: Int) {
        val element = elements[position]
        holder.itemView.apply {
            tvApiaryName.text = element.name

            this.setOnClickListener{
                val intent = Intent(context, ApiaryDetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putInt("ApiaryId",elements[position].id!!)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }
}