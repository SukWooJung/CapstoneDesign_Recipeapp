package com.example.Jachi3kki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val recipeList = arrayListOf<Recipe>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addRecipeArray()

        rv_data_list.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        ) as RecyclerView.LayoutManager?
        // rv_data_list.setHasFixedSize(true)
        // rv_data_list.adapter = DataAdapter(dataArray,this)
        rv_data_list.adapter = RecipeAdapter(recipeList, this) {
            Toast.makeText(this, "메인 메뉴에 있는 레시피가 클릭되었다.", Toast.LENGTH_SHORT).show()
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