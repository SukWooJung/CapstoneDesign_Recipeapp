package com.example.Jachi3kki.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Ingredient
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.test_list_item.*

class FridgeAdapter(
    val item: ArrayList<Ingredient>,
    val context: Context,
    val itemSelect: (Ingredient) -> Unit
) : RecyclerView.Adapter<FridgeAdapter.ExtensionViewHolder>() {
    override fun getItemCount(): Int {
        return item.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtensionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.test_list_item,
            parent,
            false
        )
        return ExtensionViewHolder(view, itemSelect)
    }

    override fun onBindViewHolder(holder: ExtensionViewHolder, position: Int) {
        holder.bind(item[position], context, position)

    }

    inner class ExtensionViewHolder(
        override val containerView: View,
        itemSelect: (Ingredient) -> Unit
    ) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(ingredient: Ingredient, context: Context, position: Int) {
            if (ingredient.cook_Img != "") {
                val resourceId = context.resources.getIdentifier(
                    ingredient.cook_Img,
                    "drawable",
                    context.packageName
                )
                cook_Img?.setImageResource(resourceId)
            } else {
                cook_Img?.setImageResource(R.mipmap.ic_launcher)
            }
            payTxt.text = ingredient.payTxt
            itemView.setOnClickListener() { itemSelect(ingredient) }
        }
    }

}
