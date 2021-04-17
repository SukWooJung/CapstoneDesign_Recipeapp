package com.example.Jachi3kki.fragment

import VerticalItemDecorator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.Bookmark
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Recipe
import com.example.Jachi3kki.databinding.BookmarkListItemBinding
import com.example.Jachi3kki.databinding.FragmentBookmarkBinding
import com.example.Jachi3kki.databinding.FragmentIngredientBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bookmark.*

class BookmarkFragment : Fragment() {

    val bookmarkList = mutableListOf<Bookmark>()
    private lateinit var binding: FragmentBookmarkBinding
    lateinit var navController: NavController
    private lateinit var BookmarkCategoryAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = nav_host_fragment.findNavController()

        addBookmarkArray()
        // 조회된 레시피 수 출력
        tv_count.text = "${bookmarkList.count()}건"

        // recyclerView에 layout Manger 설정
        rv_data_list.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        ) as RecyclerView.LayoutManager?

        // 안전성을 위해 사이즈 고정
        rv_data_list.setHasFixedSize(true)

        // 아이템간의 구분선 추가
        activity?.let { VerticalItemDecorator(it, R.drawable.line_divider, 0, 0) }?.let {
            rv_data_list.addItemDecoration(
                it
            )
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 리사이클러 뷰 출력을 위한 adapter 설정
        BookmarkCategoryAdapter = BookmarkAdapter(requireContext())

        binding.rvDataList.run{
            setHasFixedSize(true)
            adapter = BookmarkCategoryAdapter
        }
        BookmarkCategoryAdapter.replaceAll(bookmarkList)

    }



    private fun addBookmarkArray() {
        bookmarkList.add(
            Bookmark(
                "김치찌개",
                "김개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 ",
                "kimchi"
            )
        )
        bookmarkList.add(
            Bookmark(
                "된장찌개",
                "김개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 ",
                "gogi"
            )
        )
        bookmarkList.add(
            Bookmark(
                "참치찌개",
                "김개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 ",
                "mamuri"
            )
        )

    }
    private open inner class BookmarkAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var items = mutableListOf<Bookmark>()

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            return BookmarkViewHolder(
                BookmarkListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is BookmarkViewHolder) {
                var bookmark = items[position]
                if (bookmark.img != "") {
                    val resourceId = context.resources.getIdentifier(
                        bookmark.img,
                        "drawable",
                        context.packageName
                    )
                    holder.binding.imgBookmarkPicture?.setImageResource(resourceId)
                } else {
                    holder.binding.imgBookmarkPicture?.setImageResource(R.mipmap.ic_launcher)
                }
                holder.binding.tvBookmarkTitle.text = bookmark.title
                holder.binding.tvBookmarkContent.text = bookmark.content

                holder.binding.imgBookmarkIcon.setOnClickListener {
                    removeItem(position)
                }
                // 레시피 클릭되면 레시피 창으로 가도록하는 설정
                /*
                holder.itemView.setOnClickListener {
                    onItemClick(position, items[position])
                }
                */

            }
        }

        fun removeItem(position: Int) {
            if (position < this.items.size) {
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, items.size)
                bookmarkList.removeAt(position)

                binding.tvCount.text = "${items.count()}건"
            }
        }

        fun replaceAll(items: List<Bookmark>?) {
            items?.let {
                this.items.run {
                    clear()
                    addAll(it)
                    notifyDataSetChanged()
                }
            }
        }

        inner class BookmarkViewHolder(val binding: BookmarkListItemBinding) :
            RecyclerView.ViewHolder(binding.root)

    }
}

