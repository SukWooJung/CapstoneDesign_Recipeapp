package com.example.Jachi3kki.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.R

class ViewPagerAdapter(private val bgColors: ArrayList<Int>) :
    RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val pageName: TextView = itemView.findViewById(R.id.pageName)

        @SuppressLint("SetTextI18n")
        fun bind(@ColorRes bgColor: Int, position: Int) {
            pageName.text = "Page ${position+1}"
            pageName.setBackgroundColor(ContextCompat.getColor(pageName.context, bgColor))

            //여기에 데이터들 받아서 넣으면 될듯 ,, ?
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
        holder.bind(bgColors[position], position)
    }

    override fun getItemCount(): Int = bgColors.size
}