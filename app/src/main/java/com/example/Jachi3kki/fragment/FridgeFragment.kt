package com.example.Jachi3kki.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.R
import com.example.Jachi3kki.Ingredient
import kotlinx.android.synthetic.main.fragment_fridge.*
import kotlinx.android.synthetic.main.fragment_main.*

class FridgeFragment : Fragment() {
    val ingredientList = arrayListOf<Ingredient>()
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fridge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = Navigation.findNavController(view)

        super.onViewCreated(view, savedInstanceState)

        addRecipeArray()

        // recyclerView에 layout Manger 설정
        test_list.layoutManager = GridLayoutManager(
            activity,2,
            LinearLayoutManager.VERTICAL,
            false
        ) as RecyclerView.LayoutManager?


        test_list.adapter = activity?.let {
            FridgeAdapter(ingredientList, it) {
                Toast.makeText(activity, "재료클릭됨", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addRecipeArray() {
        ingredientList.add(
            Ingredient(
                "1",
                "kimchi"
            )
        )
        ingredientList.add(
            Ingredient(
                "2",
                "kimchi"
            )
        )
        ingredientList.add(
            Ingredient(
                "3",
                "kimchi"
            )
        )
        ingredientList.add(
            Ingredient(
                "4",
                "kimchi"
            )
        )
        ingredientList.add(
            Ingredient(
                "5",
                "kimchi"
            )
        )
        ingredientList.add(
            Ingredient(
                "6",
                "kimchi"
            )
        )

    }



}