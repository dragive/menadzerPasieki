package pl.pasiekaradosna.menadzerpasieki.gui.mainScreen.dashboard.adapters.apiary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_apiary_item.view.tvHiveName
import kotlinx.android.synthetic.main.fragment_apiary_item.view.tvHivesNumber
import pl.pasiekaradosna.menadzerpasieki.R
import pl.pasiekaradosna.menadzerpasieki.gui.ApiaryDetailsActivity
import pl.pasiekaradosna.menadzerpasieki.services.ApiaryManagerDbHelper
import pl.pasiekaradosna.menadzerpasieki.services.Settings.TAG

class ApiaryAdapter(
    private val elements: List<ApiaryData>
) : RecyclerView.Adapter<ApiaryAdapter.ApiaryViewHolder>() {


    class ApiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiaryViewHolder {
        return ApiaryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_apiary_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ApiaryViewHolder, position: Int) {
        val element = elements[position]
        holder.itemView.apply {
            tvHiveName.text = element.name
            tvHivesNumber.text =
                element.id?.let {
                    ApiaryManagerDbHelper(this.context).countAllHivesByApiaryId(it).toString()
                }
            this.setOnClickListener {
                try{val intent = Intent(context, ApiaryDetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putInt("ApiaryId", elements[position].id!!)
                intent.putExtras(bundle)
                context.startActivity(intent)}
                catch (err:Exception){
                    Log.e(TAG,"ERROR while apiary activity details", err)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }
}