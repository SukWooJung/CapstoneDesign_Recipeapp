package com.example.Jachi3kki

import com.example.Jachi3kki.Class.Recipe
import com.example.Jachi3kki.fragment.MainFragment
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class recipeInfo {
    companion object{
        fun fetchJson_RecipeInfo() {
            val targetIp = "118.67.132.138"
            val url = URL("http://"+targetIp+"/recipeTotal.php")
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val body = response?.body()?.string()
                    println("Success to execute recipeInfo request!")
                    //파싱을 위해 json 정보가 담긴 body를 jsonObject에 저장
                    var jsonObject = JSONObject(body)
                    val jsonArray = jsonObject.getJSONArray("recipeInfo")   //레시피 정보만 가져옴
                    val jsonArray2 = jsonObject.getJSONArray("recipeIngredient")
                    var arrayNum = jsonArray.length() //json 배열의 길이이자 전체 영양소의 개수
                    var arrayNum2 = jsonArray2.length()

                    var idArray: ArrayList<Int> = ArrayList<Int>()
                    var nameArray: ArrayList<String> = ArrayList<String>()
                    var contentArray: ArrayList<String> = ArrayList<String>()
                    var categoryArray: ArrayList<String> = ArrayList<String>()
                    var imgSrcArray: ArrayList<String> = ArrayList<String>()
                    var tempIngredientArray = mutableSetOf<String>()

                    for(i in 0 until arrayNum) {
                        idArray.add(jsonArray.getJSONObject(i).getInt("recipeId"))
                        nameArray.add(jsonArray.getJSONObject(i).getString("recipeName"))
                        contentArray.add(jsonArray.getJSONObject(i).getString("recipeContent"))
                        categoryArray.add(jsonArray.getJSONObject(i).getString("recipeCategory"))
                        imgSrcArray.add("http://"+targetIp+jsonArray.getJSONObject(i).getString("recipeImgSrc"))
                    }

                    RECIPELIST.clear()

                    for(i in 0 until idArray.size) {
                        for(j in 0 until arrayNum2) {
                            if(idArray[i]==jsonArray2.getJSONObject(j).getInt("recipeId")) {
                                tempIngredientArray.add(jsonArray2.getJSONObject(j).getString("ingredientName"))
                            }
                        }
                        val ingredientArray: ArrayList<String> = ArrayList<String>(tempIngredientArray)
                        RECIPELIST.add(
                            Recipe(idArray[i], nameArray[i], contentArray[i], categoryArray[i], imgSrcArray[i], ingredientArray)
                        )
                        tempIngredientArray.clear()
                    }
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    println("recipeInfo http 호출 실패")
                }
            })

            println("여기까진 완료됨1")
        }

        //이 밑은 레시피 디폴트 데이터
        var RECIPELIST: MutableList<Recipe> = mutableListOf<Recipe>(
            Recipe(
                1,
                "김치찌개",
                "김개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 ",
                "한식",
                "이미지 url들어감",
                arrayListOf("삼겹살", "간장", "가자미", "계란")
            ),
            Recipe(
                2,
                "된장찌개",
                "김개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 ",
                "한식",
                "이미지 url들어감",
                arrayListOf("삼겹살", "간장", "가자미", "계란")
            ),
            Recipe(
                3,
                "김치찌개",
                "김개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 김치찌개는 어쩌구 저쩌구 ",
                "한식",
                "이미지 url들어감",
                arrayListOf("삼겹살", "간장", "가자미", "계란")
            )
        )
    }
}