package com.example.Jachi3kki.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Jachi3kki.*
import com.example.Jachi3kki.Class.Recipe
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_recipe_list_item.*
import kotlinx.android.synthetic.main.main_recipe_list_item.heart_btn
import kotlinx.android.synthetic.main.main_recipe_list_item.tv_main_title
import kotlinx.android.synthetic.main.recipe_list_item.*

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
        holder.itemView.setOnClickListener {
            //Toast.makeText(context,"조회수",Toast.LENGTH_SHORT).show()
            L.i("조회수")
        }
        holder.heart_btn.setOnClickListener {
            L.i("좋아요")
        }

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
