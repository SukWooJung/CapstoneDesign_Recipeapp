package com.example.Jachi3kki.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Jachi3kki.Class.select_list
import com.example.Jachi3kki.InternalDB.FridgeIngredient
import com.example.Jachi3kki.R
import kotlinx.android.synthetic.main.refrigerator.view.*


class RefrigeratorAdapter(
    internal var context: Context,
    private var itemList: MutableList<FridgeIngredient>
) :
    RecyclerView.Adapter<RefrigeratorAdapter.RefrigeratorViewHolder>() {

    val selectlist = select_list()

    private var onItemSelectedListener: ((MutableList<Long>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefrigeratorViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.refrigerator, parent, false)

        itemView.setOnClickListener { v ->
            val id = v?.tag
            if (selectlist.selectList.contains(id)) {
                selectlist.select_remove(id as Long)
            } else {
                selectlist.select_add(id as Long)
            }
            notifyDataSetChanged()
            onItemSelectedListener?.let { it(selectlist.selectList) }
        }


        return RefrigeratorViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: RefrigeratorViewHolder, position: Int) {
        val imageUrl = "http://118.67.132.138" + itemList[position].img_src
        Glide.with(context).load(imageUrl).into(holder.txt_cart_img)
        holder.txt_cart_name.text = itemList[position].name
        holder.itemView2.tag = getItemId(position)
        holder.itemView2.isActivated =
            selectlist.selectList.contains(getItemId(position))  //선택 여부 표시
    }

    inner class RefrigeratorViewHolder(var itemView2: View) : RecyclerView.ViewHolder(itemView2) {

        var txt_cart_name: TextView
        var txt_cart_img: ImageView

        init {
            txt_cart_img = itemView2.cart_image
            txt_cart_name = itemView2.cart_item_name;
        }
    }

}