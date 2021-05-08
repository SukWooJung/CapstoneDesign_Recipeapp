package com.example.Jachi3kki.fragment

import VerticalItemDecorator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Jachi3kki.*
import com.example.Jachi3kki.Adapter.ExtensionRecipeAdapter
import com.example.Jachi3kki.Class.Bookmark
import com.example.Jachi3kki.Class.Recipe
import com.example.Jachi3kki.databinding.BookmarkListItemBinding
import com.example.Jachi3kki.databinding.FragmentBookmarkBinding
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bookmark_list_item.*
import kotlinx.android.synthetic.main.fragment_bookmark.*

class BookmarkFragment : Fragment() {

    val bookmarkList = mutableListOf<Recipe>()
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
        activity?.let { VerticalItemDecorator(it, R.drawable.horizontal_line_divider, 0, 0) }?.let {
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


    private open inner class BookmarkAdapter(private val context: Context) : RecyclerView.Adapter<BookmarkAdapter.RecipeViewHolder>() {

        var items = mutableListOf<Recipe>()

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BookmarkAdapter.RecipeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.bookmark_list_item,
                parent,
                false
            )
            return RecipeViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            holder.bind(items[position], context, position)
            holder.itemView.setOnClickListener() {
                L.i("조회수")
                //Toast.makeText(context,"조회수",Toast.LENGTH_SHORT).show()

                // 매개변수 전달 TODO
                var recipeNum = recipeInfo.RECIPELIST.indexOf(items[position])
                val intent = Intent(MainActivity.instance, PagerActivity::class.java)   //여기서 뷰페이저 연결하는 거 같은데
                intent.putExtra("recipeNum",recipeNum)
                MainActivity.instance.startActivity(intent)
            }

            holder.img_bookmark_icon.setOnClickListener{
                L.i("북마크")
                removeItem(position)
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

        fun replaceAll(items: List<Recipe>?) {
            items?.let {
                this.items.run {
                    clear()
                    addAll(it)
                    notifyDataSetChanged()
                }
            }
        }

        inner class RecipeViewHolder(
            override val containerView: View
        ) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {
            fun bind(recipe: Recipe, context: Context, position: Int) {
                Glide.with(context).load(recipe.img_src).into(img_bookmark_picture)
                tv_bookmark_title.text = recipe.name
                tv_bookmark_content.text = recipe.content
            }
        }
    }
}

