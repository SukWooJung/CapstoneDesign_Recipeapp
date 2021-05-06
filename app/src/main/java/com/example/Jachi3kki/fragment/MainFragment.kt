package com.example.Jachi3kki.fragment

import HorizontalItemDecorator
import VerticalItemDecorator
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.Jachi3kki.Adapter.MainFragmentAdapter
import com.example.Jachi3kki.Adapter.MainVitaminAdapter
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Class.Recipe
import com.example.Jachi3kki.Class.SelectedListItem
import com.example.Jachi3kki.PagerActivity
import com.example.Jachi3kki.recipeInfo
import kotlinx.android.synthetic.main.fragment_main.*
class MainFragment : Fragment() {

    class MainVitamin(val name: String, val img_src: String)
    var recipeList = arrayListOf<Recipe>()
    var vitaminList = arrayListOf<MainVitamin>()
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
        if(recipeList.isEmpty()){
            addRecipeArray()
        }
        if(vitaminList.isEmpty()){
            addVitaminArray()
        }
        toolbar.title = "Three Meals Alone"
        println("테스트: "+recipeList)

        // recyclerView에 layout Manger 설정
        rv_data_list.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        ) as RecyclerView.LayoutManager?
        rv_vitamin_list.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // 안전성을 위해 사이즈 고정
        rv_data_list.setHasFixedSize(true)
        rv_vitamin_list.setHasFixedSize(true)

        // 아이템간의 구분선 추가
        activity?.let {VerticalItemDecorator(it, R.drawable.vertical_line_divider, 0, 0) }?.let {
            rv_data_list.addItemDecoration(
                it
            )
        }
        activity?.let {HorizontalItemDecorator(it, R.drawable.horizontal_line_divider, 0, 0)}?.let{
            rv_vitamin_list.addItemDecoration(
                it
            )
        }

        rv_data_list.adapter = activity?.let { it ->
            MainFragmentAdapter(recipeList, it) {
                Log.e("Index", it.name) //어떤 아이템을 클릭했는지 확인하기위해 로그를 넣음
                Toast.makeText(activity, "메인 메뉴에 있는 레시피가 클릭되었다.", Toast.LENGTH_SHORT).show()
                //넘어갈때 00데이터 전송 ----> 반대에서 받ㄷ아서 데이터 출력ㄱ
            }
        }

        rv_vitamin_list.adapter = activity?.let { it ->
            MainVitaminAdapter(vitaminList, it) {
                Log.e("Index", it.name) //어떤 아이템을 클릭했는지 확인하기위해 로그를 넣음

                val selectedDataSet = arrayListOf<SelectedListItem>()
                selectedDataSet.add(SelectedListItem(it.name))
                navController.navigate(
                    R.id.action_mainFragment_to_ingredientFragment,
                    bundleOf("vitaminItem" to selectedDataSet)
                )
            }
        }
    }

    private fun addVitaminArray() {
        vitaminList.add(MainVitamin("비타민A", "icon_a"))
        vitaminList.add(MainVitamin("비타민B", "icon_b"))
        vitaminList.add(MainVitamin("비타민C", "icon_c"))
        vitaminList.add(MainVitamin("비타민D", "icon_d"))
        vitaminList.add(MainVitamin("비타민E", "icon_e"))
        vitaminList.add(MainVitamin("비타민K", "icon_k"))
        vitaminList.add(MainVitamin("칼슘", "icon_ca"))
        vitaminList.add(MainVitamin("인", "icon_p"))
        vitaminList.add(MainVitamin("마그네슘", "icon_mg"))
        vitaminList.add(MainVitamin("철분", "icon_fe"))
        vitaminList.add(MainVitamin("구리", "icon_cu"))
        vitaminList.add(MainVitamin("아연", "icon_zn"))
    }

    private fun addRecipeArray() {  //그냥 데이터 채워넣기
        println("여기는 addRecipeArray")
        recipeList.add(recipeInfo.RECIPELIST[0])
        recipeList.add(recipeInfo.RECIPELIST[1])
    }
}