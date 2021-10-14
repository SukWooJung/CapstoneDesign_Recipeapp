package com.example.Adapter.Jachi3kki

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.refrigerator.view.*


class RefrigeratorViewHolder(var itemView2: View) : RecyclerView.ViewHolder(itemView2){

    var txt_cart_name : TextView
    var txt_cart_img : ImageView
    init {
        txt_cart_img = itemView2.cart_image
        txt_cart_name = itemView2.cart_item_name;
    }
}
