package com.example.Jachi3kki.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Class.Recipe
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.bookmark_list_item.*

class RecipeAdapter(
    val items: ArrayList<Recipe>,
    val context: Context,
    val itemSelect: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.ExtensionViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtensionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.bookmark_list_item,
            parent,
            false
        )
        return ExtensionViewHolder(view, itemSelect)
    }

    override fun onBindViewHolder(holder: ExtensionViewHolder, position: Int) {
        holder.bind(items[position], context, position)

    }

    inner class ExtensionViewHolder(
        override val containerView: View,
        itemSelect: (Recipe) -> Unit
    ) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(recipe: Recipe, context: Context, position: Int) {
            Glide.with(context).load(recipe.img_src).into(img_bookmark_picture)
            tv_bookmark_title.text = recipe.name
            tv_bookmark_content.text = recipe.content
            itemView.setOnClickListener() { itemSelect(recipe) }
        }
    }

}
