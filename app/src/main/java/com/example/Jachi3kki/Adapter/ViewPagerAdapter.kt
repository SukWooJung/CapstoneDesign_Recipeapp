package com.example.Jachi3kki.Adapter

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.R
import kotlinx.android.synthetic.main.viewpager_content.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ViewPagerAdapter(
    val name: String = "음식 이름 .......ha.. ",
    val recipe: String = "혹시 재료.... ",
    val stepContents: List<String> = listOf(
        "단계 1 문자열 stepContent 1",
        "단계 2 문자열 stepContent 2",
        "단계 3 문자열 stepContent 3"
    ),
    val stepImageUrls: List<String> = listOf(
        "https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png",
        "https://icatcare.org/app/uploads/2021/01/email-banner.png",
        "https://www.humanesociety.org/sites/default/files/styles/2000x850/public/2020-07/kitten-510651.jpg?h=f54c7448&itok=lJefJMMQ"
    )
) :
    RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val pageNameView: TextView = itemView.pageName
        private val nameView: TextView = itemView.name
        private val recipeView: TextView = itemView.recipe
        private val contentView: TextView = itemView.content
        private val contentImageView: ImageView = itemView.contentImage

        @SuppressLint("SetTextI18n")
        fun bind(content: String, imageUrl: String, position: Int) {

            pageNameView.text = "${position + 1} 페이지"
            nameView.text = name
            recipeView.text = recipe
            contentView.text = content

            GlobalScope.launch {
                setImage(contentImageView, imageUrl)
            }
        }
    }


    fun setImage(imageView: ImageView, urlString: String) {
        val url = URL(urlString)
        val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                imageView.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.viewpager_content,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val pos = position
        holder.bind(
            stepContents[pos],
            stepImageUrls[pos],
            pos
        )
    }

    override fun getItemCount(): Int = stepContents.size
}