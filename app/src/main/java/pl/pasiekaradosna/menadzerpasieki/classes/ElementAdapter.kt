package pl.pasiekaradosna.menadzerpasieki.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_apiary.view.*
import pl.pasiekaradosna.menadzerpasieki.R

class ElementAdapter (
    private val elements: MutableList<Element>
) : RecyclerView.Adapter<ElementAdapter.ElementViewHolder>(){
    class ElementViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        return ElementViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_apiary, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val element = elements[position]

        holder.itemView.apply {
            tvOne.text = element.title
            tvOne.setOnClickListener {
                val toast = Toast.makeText(context,"TEST",Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }


}
