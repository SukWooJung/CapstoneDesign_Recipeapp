package com.example.Jachi3kki.fragment

import VerticalItemDecorator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Jachi3kki.*
import com.example.Jachi3kki.Adapter.MainFragmentAdapter
import com.example.Jachi3kki.Class.Recipe
import com.example.Jachi3kki.Class.SelectedListItem
import com.example.Jachi3kki.OuterDB.recipeInfo
import com.example.Jachi3kki.activity.MainActivity
import com.example.Jachi3kki.activity.PagerActivity
import com.example.Jachi3kki.databinding.FragmentBookmarkBinding
import com.example.Jachi3kki.log.L
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bookmark_list_item.*
import kotlinx.android.synthetic.main.fragment_bookmark.*

class BookmarkFragment : Fragment() {

    var bookmarkList = mutableListOf<Recipe>()
    private lateinit var binding: FragmentBookmarkBinding
    lateinit var navController: NavController
    private lateinit var BookmarkCategoryAdapter: BookmarkAdapter
    private val selectedAlignItem by lazy { arguments?.getParcelableArrayList<SelectedListItem>("alignmentItem") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bookmarkList = recipeInfo.BOOKMARKLIST
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = nav_host_fragment.findNavController()

        // ????????? ????????? ??? ??????
        tv_count.text = "${bookmarkList.count()}???"

        // ???????????? ????????? ???????????? ?????? ?????? ???????????? ????????? ?????? ?????????
        if (bookmarkList.count() == 0) {
            background_bookmark?.visibility = View.VISIBLE
        }
        if (bookmarkList.count() != 0) {
            background_bookmark?.visibility = View.GONE
            if (!selectedAlignItem.isNullOrEmpty()) {
                bookmarkList = sortedRecipe()
            }
        }

        // recyclerView??? layout Manger ??????
        rv_data_list.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        // ???????????? ?????? ????????? ??????
        rv_data_list.setHasFixedSize(true)

        // ??????????????? ??? ????????? ?????? adapter ??????
        BookmarkCategoryAdapter = BookmarkAdapter(requireContext())

        binding.rvDataList.run {
            setHasFixedSize(true)
            adapter = BookmarkCategoryAdapter
        }

//        BookmarkCategoryAdapter.replaceAll(bookmarkList)

        // ????????????
        condition.setOnClickListener {
            val fromInt = 1
            navController.navigate(
                R.id.action_bookmarkFragment_to_alignmentFragment,
                bundleOf("fromInt" to fromInt)
            )
        }
    }


    private open inner class BookmarkAdapter(private val context: Context) :
        RecyclerView.Adapter<BookmarkAdapter.RecipeViewHolder>() {

        var items = bookmarkList

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
                L.i("?????????")

                // ???????????? ??????
                val recipeNum = recipeInfo.RECIPELIST.indexOf(items[position])
                val intent =
                    Intent(MainActivity.instance, PagerActivity::class.java)   //????????? ???????????? ???????????? ??? ?????????

                //????????? ??????
                val recipeName = items[position].name
                items[position].viewCnt += 1
                val viewCnt = (items[position].viewCnt).toString()
                var task = MainFragmentAdapter.IncreaseView();
                task.execute("http://118.67.132.138/increaseView.php", recipeName, viewCnt)

                intent.putExtra("recipeNum", recipeNum)
                MainActivity.instance.startActivity(intent)
            }

            holder.img_bookmark_icon.setOnClickListener {
                L.i("?????????")

                if (MainActivity.id != null) {
                    if (recipeInfo.BOOKMARKLIST.contains(items[position])) {//?????? ????????????????????? ????????? ????????? ??????
                        val userId = MainActivity.id
                        val recipeName = items[position].name
                        items[position].likeCnt -= 1
                        val likeCnt = (items[position].likeCnt).toString()
                        val task = MainFragmentAdapter.DeleteData()
                        task.execute(
                            "http://118.67.132.138/deleteDb.php",
                            userId,
                            recipeName,
                            likeCnt
                        )
                        recipeInfo.BOOKMARKLIST.remove(items[position]) //??? ???????????? ???????????? ?????? ????????? ???????????? ?????????????????? ????????????

                        holder.img_bookmark_icon.setImageResource(R.drawable.icon_bookmark)
                        Toast.makeText(context, "??????????????? ?????????????????????", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "???????????? ???????????????", Toast.LENGTH_SHORT).show()
                }
                removeItem(position)
            }


        }


        fun removeItem(position: Int) {
            if (position < this.items.size) {
                items.removeAt(position) //?????? ????????? ?????? ????????? ????????????, ????????? ??? ?????? ????????? ?????????
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, items.size)
                //bookmarkList.removeAt(position) ?????? ???????????? ????????? ?????????. ??? ??? recipeInfo.BOOKMARKLIST??? ???????????? ????????? ?????? ???????????? ??????

                if (bookmarkList.count() == 0) {
                    background_bookmark?.visibility = View.VISIBLE
                }
                binding.tvCount.text = "${items.count()}???"

            }
        }

        fun replaceAll(items: List<Recipe>?) {
            items?.let {
                this.items.run {
                    clear()
                    addAll(it)  //????????? items??? BookmarkFragment??? bookmarkList??? ?????? ?????????
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

                if (MainActivity.id != null) {
                    img_bookmark_icon.setImageResource(R.drawable.icon_bookmark_check)
                }
                bookmark_tv_likeCnt.text = "${recipe.likeCnt}"
                bookmark_tv_viewCnt.text = "${recipe.viewCnt}"

            }
        }
    }

    private fun sortedRecipe(): MutableList<Recipe> {
        val sortedRecipe = bookmarkList as ArrayList<Recipe>
        if (!selectedAlignItem.isNullOrEmpty()) {
            val likeCnt = selectedAlignItem!![0].data
            val viewCnt = selectedAlignItem!![1].data
            if (likeCnt == "????????????" && viewCnt == "????????????") {
            } else if (likeCnt == "???????????????" && viewCnt == "??????????????????") {
                sortedRecipe.sortWith(compareBy({ -it.likeCnt }, { -it.viewCnt }))
            } else if (likeCnt == "???????????????" && viewCnt == "??????????????????") {
                sortedRecipe.sortWith(compareBy({ it.likeCnt }, { it.viewCnt }))
            } else if (likeCnt == "???????????????" && viewCnt == "??????????????????") {
                sortedRecipe.sortWith(compareBy({ -it.likeCnt }, { it.viewCnt }))
            } else if (likeCnt == "???????????????" && viewCnt == "??????????????????") {
                sortedRecipe.sortWith(compareBy({ it.likeCnt }, { -it.viewCnt }))
            } else if (likeCnt == "????????????" && viewCnt == "??????????????????") {
                sortedRecipe.sortWith(compareBy { -it.viewCnt })
            } else if (likeCnt == "????????????" && viewCnt == "??????????????????") {
                sortedRecipe.sortWith(compareBy { it.viewCnt })
            } else if (likeCnt == "???????????????" && viewCnt == "????????????") {
                sortedRecipe.sortWith(compareBy { -it.likeCnt })
            } else if (likeCnt == "???????????????" && viewCnt == "????????????") {
                sortedRecipe.sortWith(compareBy { it.likeCnt })
            }
        }
        return sortedRecipe
    }

}

