package com.example.Jachi3kki.OuterDB

import com.example.Jachi3kki.Class.Vitamin
import com.example.Jachi3kki.InternalDB.FridgeIngredient
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class Category
private constructor(val src: Array<String>) {
    companion object {
        fun addFrom_fridge(ingredient_list: Array<String>) {
            CLASS3.put("냉장고",
                Category(ingredient_list)
            )
        }

        fun fetchJson_Ingredient(ingredientList: MutableList<FridgeIngredient>) {
            //냉장고 데이터 추가
            CLASS1.clear()
            CLASS1["냉장고"] = "냉장고"
            CLASS2["냉장고"] =
                Category(arrayOf("냉장고"))
            val fridgeSet = mutableSetOf<String>()
            for ( i in 0 until ingredientList.size) {
                if (ingredientList[i].activation != 0)
                    fridgeSet.add(ingredientList[i].name)
            }
            val fridgelist = fridgeSet.toTypedArray()
            CLASS3["냉장고"] =
                Category(fridgelist)

            val url = URL("http://118.67.132.138/ingredient.php")
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback{
                override fun onResponse(call: Call?, response: Response?) {
                    val body = response?.body()?.string()//body는 php의 내용을 그대로 담고 있음
                    println("Success to execute request!")

                    //파싱을 위해 json 정보가 담긴 body를 jsonObject에 저장
                    val jsonObject = JSONObject(body)
                    val jsonArray = jsonObject.getJSONArray("ingredient")
                    val arrayNum = jsonArray.length() //json 배열의 길이이자 소분류 개수와 같음
                    val largeSet = mutableSetOf<String>() //대분류 담을 set
                    val middleTempSet = mutableSetOf<String>()//각 대분류 별 중분류를 담을 set
                    val middleSet = mutableSetOf<String>() //모든 중분류 담을 set
                    val smallSet = mutableSetOf<String>() //소분류 담을 set
                    var middleNum: Int //중분류 개수담을 용도임

                    for(i in 0 until arrayNum) { //대분류 데이터 얻기
                        largeSet.add(jsonArray.getJSONObject(i).getString("largeCategory"))
                    }
                    val largeList = largeSet.toList() //그냥 형변환 set to list
                    val largeNum = largeList.size //대분류 종류 개수
                    for(i in 0 until largeNum) {//대분류 데이터 교체
                        CLASS1[largeList[i]] = largeList[i]
                    }
                    for (i in 0 until largeNum) {//중분류 데이터 교체
                        for (j in 0 until arrayNum) {
                            if(largeList[i].equals(jsonArray.getJSONObject(j).getString("largeCategory"))) {
                                middleTempSet.add(jsonArray.getJSONObject(j).getString("middleCategory"))
                                middleSet.add(jsonArray.getJSONObject(j).getString("middleCategory"))
                            }
                        }
                        val middleArray: Array<String> = middleTempSet.toTypedArray()
                        middleTempSet.clear()
                        CLASS2[largeList[i]] =
                            Category(middleArray)
                    }
                    val middleList= middleSet.toList()
                    middleNum = middleList.size //모든 중분류 개수
                    for (i in 0 until middleNum) {// 소분류 데이터 교체
                        for (j in 0 until arrayNum) {
                            if(middleList[i].equals(jsonArray.getJSONObject(j).getString("middleCategory")))
                                smallSet.add(jsonArray.getJSONObject(j).getString("smallCategory"))
                        }
                        val smallArray: Array<String> = smallSet.toTypedArray()
                        smallSet.clear()
                        CLASS3[middleList[i]] =
                            Category(smallArray)
                    }
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    println("Fail to execute request!")
                }
            })
        }

        fun fetchJson_vitamin() {
            val targetIp = "118.67.132.138"
            val url = URL("http://"+targetIp+"/nutrition_info.php")
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback{
                override fun onResponse(call: Call?, response: Response?) {
                    val body = response?.body()?.string()//body는 php의 내용을 그대로 담고 있음
                    println("Success to execute nutrition request!")

                    //파싱을 위해 json 정보가 담긴 body를 jsonObject에 저장
                    var jsonObject = JSONObject(body)
                    val jsonArray = jsonObject.getJSONArray("nutrition_info")
                    val arrayNum = jsonArray.length() //json 배열의 길이이자 전체 영양소의 개수
                    var nutritionName: MutableList<String> = mutableListOf<String>()
                    var nutritionDesc: MutableList<String> = mutableListOf<String>()
                    var nutritionImgPath: MutableList<String> = mutableListOf<String>()

                    for(i in 0 until arrayNum) {
                        nutritionName.add(jsonArray.getJSONObject(i).getString("nutritionName"))
                        nutritionDesc.add(jsonArray.getJSONObject(i).getString("nutritionDescription"))
                        nutritionImgPath.add(targetIp+jsonArray.getJSONObject(i).getString("nutritionImage"))
                    }
                    VITAMINDATA.clear()
                    for(i in 0 until arrayNum) {
                        VITAMINDATA[nutritionName[i]] = Vitamin(nutritionName[i], nutritionDesc[i], nutritionImgPath[i])
                    }
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    println("Fail to execute request!")
                }
            })
        }

        //그냥 형태만 다듬는 용도
        var VITAMINDATA = mutableMapOf(
            "비타민A" to Vitamin(
                "비타민A", "비타민A는 어쩌구 저쩌구 비타민A는 어쩌구 저쩌구 비타민A는 어쩌구 저쩌구 비타민A는 어쩌구 저쩌구 ",
                "gogi"
            )
        )

        var CLASS1 = mutableMapOf(
            "육류" to ("육류")
        )

        var CLASS2 = mutableMapOf(
            "육류" to Category(
                arrayOf(
                    "돼지고기",
                    "소고기",
                    "닭고기",
                    "오리고기",
                    "기타"
                )
            )
        )

        var CLASS3 = mutableMapOf(
            "돼지고기" to Category(
                arrayOf(
                    "삼겹살",
                    "가브리살",
                    "뒷다리살",
                    "항정살",
                    "앞다리살",
                    "등심",
                    "사태",
                    "갈비",
                    "안심",
                    "갈매기살"
                )
            )
        )

        fun fetchJson_vitaminToIngredient() {
            GETINGREDIENT.clear()   //외부에서 데이터 읽어오므로 디폴트 데이터는 날림
            val targetIp = "118.67.132.138"
            val url = URL("http://"+targetIp+"/nutritionIngredient.php")   //재료별 비타민 가져오기
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback{
                override fun onResponse(call: Call?, response: Response?) {
                    val body = response?.body()?.string()//body는 php의 내용을 그대로 담고 있음
                    println("Success to execute nutrition request!")

                    var jsonObject = JSONObject(body)
                    val jsonArray = jsonObject.getJSONArray("nutrition_info")
                    val arrayNum = jsonArray.length()
                    var vitaminSet = mutableSetOf<String>() //비타민 담을 set
                    var ingredientTempSet = mutableSetOf<String>()//각 비타민 별 포함 재료를 담을 set

                    for(i in 0 until arrayNum) { //대분류(비타민) 데이터 얻기
                        vitaminSet.add(jsonArray.getJSONObject(i).getString("nutritionName"))
                    }

                    //largeList는 비타민 구분이 담겨있는 리스트
                    val vitaminList = vitaminSet.toList() //그냥 형변환 set to list. 잘됨
                    val vitaminNum = vitaminList.size //대분류 종류 개수

                    for (i in 0 until vitaminNum) {//중분류 데이터 교체
                        for (j in 0 until arrayNum) {
                            if(vitaminList[i].equals(jsonArray.getJSONObject(j).getString("nutritionName"))) {
                                ingredientTempSet.add(jsonArray.getJSONObject(j).getString("ingredientName"))
                            }
                        }
                        val ingredientArray: Array<String> = ingredientTempSet.toTypedArray()   //데이터는 잘 들어감
                        ingredientTempSet.clear()
                        GETINGREDIENT[vitaminList[i]] =
                            Category(
                                ingredientArray
                            )   //비타민 별 재료를 넣음
                    }
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    println("Fail to execute request!")
                }
            })
        }

        var GETINGREDIENT = mutableMapOf(
            "비타민A" to Category(
                arrayOf(
                    "시금치",
                    "닭가슴살",
                    "콩나물",
                    "숙주나물",
                    "고등어"
                )
            ),
            "비타민B1" to Category(
                arrayOf(
                    "시금치",
                    "미역",
                    "한치",
                    "새송이버섯",
                    "콩나물",
                    "숙주나물",
                    "고등어"
                )
            )
        )
    }
}