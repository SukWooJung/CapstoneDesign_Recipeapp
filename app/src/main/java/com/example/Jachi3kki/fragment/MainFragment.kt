package com.example.Jachi3kki.fragment

import VerticalItemDecorator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.Adapter.RecipeAdapter
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Class.Recipe
import com.example.Jachi3kki.PagerActivity
import com.example.Jachi3kki.recipeInfo
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    var recipeList = arrayListOf<Recipe>()
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        addRecipeArray()
        toolbar.title = "Three Meals Alone"
        println("테스트: "+recipeList)

        // recyclerView에 layout Manger 설정
        rv_data_list.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        ) as RecyclerView.LayoutManager?

        // 안전성을 위해 사이즈 고정
        rv_data_list.setHasFixedSize(true)

        // 아이템간의 구분선 추가
        activity?.let {VerticalItemDecorator(it, R.drawable.line_divider, 0, 0) }?.let {
            rv_data_list.addItemDecoration(
                it
            )
        }

        rv_data_list.adapter = activity?.let { it ->
            RecipeAdapter(recipeList, it) {
                Log.e("Index", it.name) //어떤 아이템을 클릭했는지 확인하기위해 로그를 넣음
                Toast.makeText(activity, "메인 메뉴에 있는 레시피가 클릭되었다.", Toast.LENGTH_SHORT).show()

                startActivity(Intent(view.context, PagerActivity::class.java))
                //넘어갈때 00데이터 전송 ----> 반대에서 받ㄷ아서 데이터 출력ㄱ

                //startActivity(Intent(현재 액티비티, 이동할 액티비티)) --> 일단 냉장고 선택완료에만 넣었음
                // 재료선택, 비타민선택에는 안넣었어요ㅛ ,,,, ('-'*)
            }
        }
    }

    private fun addRecipeArray() {  //그냥 데이터 채워넣기
        //recipeInfo.fetchJson_RecipeInfo()
        recipeList.add(recipeInfo.RECIPELIST[0])
        recipeList.add(recipeInfo.RECIPELIST[1])
        recipeList.add(recipeInfo.RECIPELIST[2])
    }
}