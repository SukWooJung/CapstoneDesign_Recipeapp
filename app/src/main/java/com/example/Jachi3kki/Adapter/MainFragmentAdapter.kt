package com.example.Jachi3kki.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Jachi3kki.Class.Recipe
import com.example.Jachi3kki.MainActivity
import com.example.Jachi3kki.PagerActivity
import com.example.Jachi3kki.R
import com.example.Jachi3kki.recipeInfo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_recipe_list_item.*

class MainFragmentAdapter(
    val items: ArrayList<Recipe>,
    val context: Context,
    val itemSelect: (Recipe) -> Unit
) : RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.main_recipe_list_item,
            parent,
            false
        )
        return MainFragmentViewHolder(view, itemSelect)
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.bind(items[position], context, position)

    }

    inner class MainFragmentViewHolder(
        override val containerView: View,
        itemSelect: (Recipe) -> Unit
    ) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(recipe: Recipe, context: Context, position: Int) {
            Glide.with(context).load(recipe.img_src).into(main_img_bookmark_picture)
            main_tv_bookmark_title.text = recipe.name
            main_tv_bookmark_content.text = recipe.content
            var recipeNum = recipeInfo.RECIPELIST.indexOf(recipe)
            main_img_bookmark_picture.setOnClickListener {
                val intent = Intent(MainActivity.instance, PagerActivity::class.java)
                intent.putExtra("recipeNum",recipeNum)
                MainActivity.instance.startActivity(intent)
            }
            main_img_bookmark_icon.setOnClickListener {
                itemSelect(recipe)
            }
            tv_main_title.text = when(position){
                0 -> "오늘의 추천 레시피"
                1 -> "Hot 레시피"
                else -> "나오면 안됨"
            }
        }
    }

}
