package com.example.Jachi3kki.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.Bookmark
import com.example.Jachi3kki.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.bookmark_list_item.*
import kotlinx.android.synthetic.main.bookmark_list_item.constraintLayout
import kotlinx.android.synthetic.main.bookmark_list_item.img
import kotlinx.android.synthetic.main.recipe_list_item.*


class BookmarkAdapter(
    val items: ArrayList<Bookmark>,
    val context: Context,
    val itemSelect: (Bookmark) -> Unit
) : RecyclerView.Adapter<BookmarkAdapter.ExtensionViewHolder>() {
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
        itemSelect: (Bookmark) -> Unit
    ) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(bookmark: Bookmark, context: Context, position: Int) {
            if (bookmark.img != "") {
                val resourceId = context.resources.getIdentifier(
                    bookmark.img,
                    "drawable",
                    context.packageName
                )
                img?.setImageResource(resourceId)
            } else {
                img?.setImageResource(R.mipmap.ic_launcher)
            }
            bk_title.text = bookmark.title
            bk_content.text = bookmark.content
            itemView.setOnClickListener() { itemSelect(bookmark) }
        }
    }

}
