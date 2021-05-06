package com.example.Jachi3kki.fragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.MainActivity
import com.example.Jachi3kki.PagerActivity
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Recipe
import com.example.Jachi3kki.databinding.FragmentRecipeBinding
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recipe_list_item.*

class RecipeAdapter(
    val items: ArrayList<Recipe>,
    val context: Context
) : RecyclerView.Adapter<RecipeAdapter.ExtensionViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtensionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recipe_list_item,
            parent,
            false
        )
        return ExtensionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExtensionViewHolder, position: Int) {
        holder.bind(items[position], context, position)

    }

    inner class ExtensionViewHolder(
        override val containerView: View
    ) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(recipe: Recipe, context: Context, position: Int) {
            if (recipe.img != "") {
                val resourceId = context.resources.getIdentifier(
                    recipe.img,
                    "drawable",
                    context.packageName
                )
                img_recipe?.setImageResource(resourceId)
            } else {
                img_recipe?.setImageResource(R.mipmap.ic_launcher)
            }
            tv_recipe_title.text = recipe.title
            tv_content.text = recipe.content
            tv_main_title.text = when(position){
                0-> "오늘의 핫 레시피"
                1-> "오늘의 추천 레시피"
                2-> "오늘의 영양소에 따른 레시피"
                else -> "이게 나올리 없음"
            }
            itemView.setOnClickListener() {
                // 매개변수 전달 TODO
                val intent = Intent(MainActivity.instance, PagerActivity::class.java)
                MainActivity.instance.startActivity(intent)
            }
        }
    }

}
