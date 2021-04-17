package com.example.Jachi3kki.fragment

import VerticalItemDecorator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.L
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Recipe
import com.example.Jachi3kki.SelectedListItem
import com.example.Jachi3kki.databinding.CategorySelectedListItemBinding
import kotlinx.android.synthetic.main.fragment_main.rv_data_list
import kotlinx.android.synthetic.main.fragment_recipe.*
import kotlinx.android.synthetic.main.fragment_recipe.rc_count

class RecipeFragment : Fragment() {
    val recipeList = arrayListOf<Recipe>()
    lateinit var navController: NavController

    private lateinit var selectedAdapter: SelectedListAdapter
    private val selectedMenuItem  by lazy { arguments?.getParcelableArrayList<SelectedListItem>("item")}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        L.i("::::intent " + selectedMenuItem)
        // 데이터 집어넣기
        if (recipeList.isEmpty()) addRecipeArray()

        // 조회된 레시피 수 출력
        rc_count.text = "${recipeList.count()}건"

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
        rv_data_list.adapter = RecipeAdapter(recipeList, requireContext()) {
            Toast.makeText(activity, "메인 메뉴에 있는 레시피가 클릭되었다.", Toast.LENGTH_SHORT).show()
        }


        btn_alignment.setOnClickListener {
            val dialogView: View =
                layoutInflater.inflate(R.layout.fragment_alignment, null)

            val builder = activity?.let { it1 -> AlertDialog.Builder(it1) }
            if (builder != null) {
                builder.setView(dialogView)
                builder.show()
            }
        }

//        btn_alignment.setOnClickListener {
//            navController.navigate(R.id.action_recipeFragment_to_alignmentFragment)
//        }

        btn_detail.setOnClickListener {
            navController.navigate(R.id.action_recipeFragment_to_detailFragment)
        }


        selectedAdapter = object : SelectedListAdapter(requireContext()){
            override fun onItemClick(index: Int, data: SelectedListItem) {
                L.i("::클릭시...이벤트")
            }

        }

        rv_selected_item.run {
            setHasFixedSize(true)
            adapter = selectedAdapter
        }

        selectedAdapter.replaceAll(selectedMenuItem?.toList())
    }

    private fun addRecipeArray() {
        recipeList.add(
            Recipe(
                "김치찌개",
                "김개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 ",
                "kimchi"
            )
        )
        recipeList.add(
            Recipe(
                "된장찌개",
                "김개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 ",
                "gogi"
            )
        )
        recipeList.add(
            Recipe(
                "참치찌개",
                "김개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 ",
                "mamuri"
            )
        )

    }


    private abstract inner class SelectedListAdapter(private val context: Context) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        abstract fun onItemClick(index: Int, data: SelectedListItem)

        var items = mutableListOf<SelectedListItem>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return CategorySelectedListViewHolder(
                CategorySelectedListItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }

        fun replaceAll(items: List<SelectedListItem>?) {
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
            if (holder is CategorySelectedListViewHolder) {
                val title = items[position].data
                holder.binding.tvSelectedTitle.text = title
                holder.itemView.setOnClickListener {
                    onItemClick(position, items[position])
                }
            }
        }

        inner class CategorySelectedListViewHolder(val binding: CategorySelectedListItemBinding) :
            RecyclerView.ViewHolder(binding.root)

    }


}