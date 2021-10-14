package com.example.Jachi3kki.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.Class.CategoryListItem
import com.example.Jachi3kki.databinding.CategorySelectedListItemBinding

abstract class SelectedListAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun onItemClick(index: Int, data: CategoryListItem)

    var items = mutableListOf<CategoryListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategotySelectedListViewHolder(
            CategorySelectedListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun replaceAll(items: List<CategoryListItem>?) {
        items?.let {
            this.items.run {
                clear()
                addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    fun removeItem(position: Int) {
        if (position < this.items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategotySelectedListViewHolder) {
            val title = items[position].data
            holder.binding.tvSelectedTitle.text = title
            holder.itemView.setOnClickListener {
                try {
                    onItemClick(position, items[position])
                } catch (e: IndexOutOfBoundsException) {
                    print("IndexOutOfBoundsException 오류 발생")
                }
            }
        }
    }

    inner class CategotySelectedListViewHolder(val binding: CategorySelectedListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}