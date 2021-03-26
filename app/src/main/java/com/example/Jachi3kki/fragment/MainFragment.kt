package com.example.Jachi3kki.fragment

import VerticalItemDecorator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Recipe
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    val recipeList = arrayListOf<Recipe>()
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

        addRecipeArray()

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

        rv_data_list.adapter = activity?.let {
            RecipeAdapter(recipeList, it) {
                Toast.makeText(activity, "메인 메뉴에 있는 레시피가 클릭되었다.", Toast.LENGTH_SHORT).show()
            }
        }

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



}