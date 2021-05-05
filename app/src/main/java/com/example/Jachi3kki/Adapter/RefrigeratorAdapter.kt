package com.example.Jachi3kki

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Adapter.Jachi3kki.RefrigeratorViewHolder
import com.example.Jachi3kki.InternalDB.FridgeIngredient
import com.example.Jachi3kki.Model.Item


class RefrigeratorAdapter(internal var  context: Context, internal var itemList:MutableList<FridgeIngredient>) :
    RecyclerView.Adapter<RefrigeratorViewHolder>() {

    val selectlist = select_list()

    //    var selectionList = ArrayList<Long>()
    var onItemSelectedListener: ((MutableList<Long>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefrigeratorViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.refrigerator, parent, false)

        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val id = v?.tag
                if (selectlist.selectList.contains(id)) {
                    selectlist.select_remove(id as Long)
                } else {
                    selectlist.select_add(id as Long)
                }
                notifyDataSetChanged()
                onItemSelectedListener?.let { it(selectlist.selectList) }
            }
        })


        return RefrigeratorViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: RefrigeratorViewHolder, position: Int) {
        var imageUrl = "http://118.67.132.138" + itemList[position].img_src
        Glide.with(context).load(imageUrl).into(holder.txt_cart_img)
        //println("테스트용: "+itemList[position].img_src)
        holder.txt_cart_name.text = itemList[position].name
        holder.itemView2.tag = getItemId(position)
        holder.itemView2.isActivated =
            selectlist.selectList.contains(getItemId(position))  //선택 여부 표시


    }
}