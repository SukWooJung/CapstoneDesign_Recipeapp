package com.example.Jachi3kki

import java.io.IOException
import java.net.URL
import okhttp3.*
import org.json.JSONObject

class Category
private constructor(val src: Array<String>) {
    companion object {
        fun fetchJson() {
            val url = URL("http://118.67.132.138/ingredient.php")
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback{
                override fun onResponse(call: Call?, response: Response?) {
                    val body = response?.body()?.string()//body는 php의 내용을 그대로 담고 있음
                    println("Success to execute request!")

                    //파싱을 위해 json 정보가 담긴 body를 jsonObject에 저장
                    var jsonObject = JSONObject(body)
                    val jsonArray = jsonObject.getJSONArray("ingredient")
                    val arrayNum = jsonArray.length() //json 배열의 길이이자 소분류 개수와 같음
                    var largeSet = mutableSetOf<String>() //대분류 담을 set
                    var middleTempSet = mutableSetOf<String>()//각 대분류 별 중분류를 담을 set
                    var middleSet = mutableSetOf<String>() //모든 중분류 담을 set
                    var smallSet = mutableSetOf<String>() //소분류 담을 set
                    var middleNum = 0 //중분류 개수담을 용도임

                    //데이터 초기화(모두 지우기)
                    CLASS1.clear()
                    CLASS2.clear()
                    CLASS3.clear()

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
                        CLASS2[largeList[i]] = Category(middleArray)
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
                        CLASS3[middleList[i]] = Category(smallArray)
                    }
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    println("Fail to execute request!")
                }
            })
        }

        var CLASS1 = mutableMapOf(
            "육류" to ("육류")
        )

        var CLASS2 = mutableMapOf(
            "육류" to  Category(arrayOf("돼지고기","소고기","닭고기","오리고기","기타"))
        )

        var CLASS3 = mutableMapOf(
            "돼지고기" to  Category(arrayOf("삼겹살","가브리살","뒷다리살","항정살","앞다리살","등심","사태","갈비","안심","갈매기살"))
        )

        var VITAMINDATA = mutableMapOf(
            "비타민A" to Vitamin("비타민A",  "비타민A는 어쩌구 저쩌구 비타민A는 어쩌구 저쩌구 비타민A는 어쩌구 저쩌구 비타민A는 어쩌구 저쩌구 ",
                "gogi" ),
            "비타민B" to Vitamin("비타민B", "비타민B 는 어쩌구 저쩌구 비타민B 는 어쩌구 저쩌구 비타민B 는 어쩌구 저쩌구 비타민B 는 어쩌구 저쩌구", "kimchi"),
            "비타민C" to Vitamin("비타민C", "비타민C 는 어쩌구 저쩌구 비타민C 는 어쩌구 저쩌구 비타민C 는 어쩌구 저쩌구 비타민C 는 어쩌구 저쩌구", "mamuri")

        )
    }
}