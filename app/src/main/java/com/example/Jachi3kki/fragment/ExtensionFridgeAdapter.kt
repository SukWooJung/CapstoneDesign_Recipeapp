package com.example.Jachi3kki.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Ingredient
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.test_list_item.view.*

class FridgeAdapter(val item: ArrayList<Ingredient>, val context: FragmentActivity, val itemSelect: (Ingredient) -> Unit) : RecyclerView.Adapter<FridgeAdapter.ExtensionViewHolder>() {
    val selectionList = mutableListOf<Long>()
    var onItemSelectedListener : ((MutableList<Long>)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ExtensionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_list_item,parent,false)

        view.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val id = v?.tag
                if(selectionList.contains(id)) {
                    selectionList.remove(id)
                }
                else {
                    selectionList.add(id as Long)
                }
                notifyDataSetChanged()
                onItemSelectedListener?.let{it(selectionList)}
            }
        })
        return ExtensionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ExtensionViewHolder, position: Int) {
        holder.containerView.payTxt.text = item[position].payTxt
        holder.containerView.tag = getItemId(position)
        holder.containerView.isActivated = selectionList.contains(getItemId(position))


    }

    inner class ExtensionViewHolder(override val containerView: View) :RecyclerView.ViewHolder(containerView), LayoutContainer {
    }

}
