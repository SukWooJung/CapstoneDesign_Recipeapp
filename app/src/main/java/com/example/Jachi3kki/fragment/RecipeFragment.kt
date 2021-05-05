package com.example.Jachi3kki.fragment

import VerticalItemDecorator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.Adapter.ExtensionRecipeAdapter
import com.example.Jachi3kki.Adapter.MainFragmentAdapter
import com.example.Jachi3kki.Adapter.SelectedListAdapter
import com.example.Jachi3kki.Class.CategoryListItem
import com.example.Jachi3kki.L
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Class.Recipe
import com.example.Jachi3kki.Class.SelectedListItem
import com.example.Jachi3kki.recipeInfo
import kotlinx.android.synthetic.main.fragment_main.rv_data_list
import kotlinx.android.synthetic.main.fragment_recipe.*
import kotlinx.android.synthetic.main.fragment_recipe.rc_count

class RecipeFragment : Fragment() {
    lateinit var recipeList : ArrayList<Recipe>
    lateinit var navController: NavController
    var selectedMenuItems = mutableSetOf<String>()

    private lateinit var selectedAdapter: SelectedListAdapter
    private val selectedMenuItem  by lazy { arguments?.getParcelableArrayList<SelectedListItem>("item")}
    private val selectedAlignItem by lazy{arguments?.getParcelableArrayList<SelectedListItem>("alignmentItem")}
    private val selectedDetailItem by lazy{arguments?.getParcelableArrayList<SelectedListItem>("detailItem")}

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

        // 상세조건 버튼클릭
        btn_detail.setOnClickListener {
            val selectedDataSet by lazy{
                ArrayList<SelectedListItem>().also { list->
                    selectedMenuItems.forEach{
                        list.add(SelectedListItem(it))
                    }
                }
            }
            navController.navigate(R.id.action_recipeFragment_to_detailFragment, bundleOf("item" to selectedDataSet))
        }

        // 정렬조건 버튼클릭
        btn_alignment.setOnClickListener {
            val selectedDataSet by lazy{
                ArrayList<SelectedListItem>().also { list->
                    selectedMenuItems.forEach{
                        list.add(SelectedListItem(it))
                    }
                }
            }
            navController.navigate(R.id.action_recipeFragment_to_alignmentFragment, bundleOf("item" to selectedDataSet))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 선택된 재료로 레시피 검색, 편의를 위한 데이터 형변환

        selectedMenuItem?.forEach{
            selectedMenuItems.add(it.data)
        }


        // 레시피 검색
        // DB에 레시피 데이터가 있어야됨
        recipeList = findRecipe(selectedMenuItems.toMutableList() as ArrayList<String>)

        // 정렬 조건 반영
        println("test" + selectedAlignItem)

        // 필터 조건 반영
        println("test" + selectedDetailItem)

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
//        activity?.let { VerticalItemDecorator(it, R.drawable.vertical_line_divider, 0, 0) }?.let {
//            rv_data_list.addItemDecoration(
//                it
//            )
//        }

        rv_data_list.adapter = ExtensionRecipeAdapter(
            recipeList,
            requireContext()
        ) {
            Toast.makeText(activity, "메인 메뉴에 있는 레시피가 클릭되었다.", Toast.LENGTH_SHORT).show()
        }

        // 셀렉티드 업덥터
        selectedAdapter = object : SelectedListAdapter(requireContext()){
            override fun onItemClick(index: Int, item: CategoryListItem) {
                //선택한 리스트뷰 클릭시 떨어지는 이벤트트
                L.i(":::::::::::::::선택한 선택한 카테고리 " + item.data)
                selectedAdapter.removeItem(index)
                selectedMenuItems.remove(item.data)
                recipeList = findRecipe(selectedMenuItems.toMutableList() as ArrayList<String>)
                rc_count.text = "${recipeList.count()}건"

                rv_data_list.adapter = MainFragmentAdapter(
                    recipeList,
                    requireContext()
                ) {
                    Toast.makeText(activity, "메인 메뉴에 있는 레시피가 클릭되었다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        rv_selected_item.run {
            setHasFixedSize(true)
            adapter = selectedAdapter
        }

        reLoadSelectedCategory()
    }

    // 선택된 재료들을 모두 포함하는 모든 레시피를 찾음
    private fun findRecipe(ingredientArr: ArrayList<String>): ArrayList<Recipe> {
        var tempRecipeList = ArrayList<Recipe>()
        recipeInfo.RECIPELIST.forEach{
            if(it.ingredientArr.containsAll(ingredientArr))
                tempRecipeList.add(it)
        }
        return tempRecipeList
    }

    private fun reLoadSelectedCategory() {
        //다시 카테고리 화면 돌아올시.. 선택한 메뉴들 ListUp 오른쪽에 back화살표 모양
        val selectedDataSet = arrayListOf<CategoryListItem>()
        selectedMenuItems.forEach {
            selectedDataSet.add(CategoryListItem(it, false))
        }
        L.i("::::selectedDataSet " + selectedDataSet)
        selectedAdapter.replaceAll(selectedDataSet)
    }
}