package com.example.Jachi3kki

import com.example.Jachi3kki.Class.Recipe
import com.example.Jachi3kki.fragment.MainFragment
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.sql.Types.NULL

class recipeInfo {
    companion object{
        fun fetchJson_RecipeInfo() {
            val targetIp = "118.67.132.138"
            val url = URL("http://"+targetIp+"/recipe_total.php")
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val body = response?.body()?.string()
                    println("Success to execute recipeInfo request!")

                    //파싱을 위해 json 정보가 담긴 body를 jsonObject에 저장
                    var jsonObject = JSONObject(body)
                    val jsonArray = jsonObject.getJSONArray("recipe_manual")   //레시피 상세정보가 들어감. 조리 방법 등등. null값도 있으므로 조심
                    val jsonArray2 = jsonObject.getJSONArray("recipe_ingredient")//(레시피에 들어가는 재료가 포함됨. null값도 있으므로 조심)
                    var arrayNum = jsonArray.length() //레시피의 개수
                    var arrayNum2 = jsonArray2.length()//레시피의 개수

                    //recipe_manual 에서 가져올 데이터들
                    var idArray: ArrayList<Int> = ArrayList<Int>()  //레시피 아이디
                    var nameArray: ArrayList<String> = ArrayList<String>()  //레시피 이름
                    var contentArray: ArrayList<String> = ArrayList<String>()   //레시피 조리방법
                    var categoryArray: ArrayList<String> = ArrayList<String>()  //음식 종류(밥, 반찬 등등)
                    var imgSrcArray: ArrayList<String> = ArrayList<String>()    //음식 메인 이미지
                    var ingredientsArray: ArrayList<String> = ArrayList<String>()   //한줄로된 필요 재료들
                    var likeArray: ArrayList<Int> = ArrayList<Int>()    //좋아요수
                    var viewArray: ArrayList<Int> = ArrayList<Int>()    //조회수
                    //여기까진 하나에 하나씩만 필요. 밑에 나올 것들은 null값도 걸러내야하는거라 많이 더러워질 것임
                    var manual01List: ArrayList<String> = ArrayList<String>()
                    var img01List: ArrayList<String> = ArrayList<String>()
                    var manual02List: ArrayList<String> = ArrayList<String>()
                    var img02List: ArrayList<String> = ArrayList<String>()
                    var manual03List: ArrayList<String> = ArrayList<String>()
                    var img03List: ArrayList<String> = ArrayList<String>()
                    var manual04List: ArrayList<String> = ArrayList<String>()
                    var img04List: ArrayList<String> = ArrayList<String>()
                    var manual05List: ArrayList<String> = ArrayList<String>()
                    var img05List: ArrayList<String> = ArrayList<String>()
                    var manual06List: ArrayList<String> = ArrayList<String>()
                    var img06List: ArrayList<String> = ArrayList<String>()
                    var manual07List: ArrayList<String> = ArrayList<String>()
                    var img07List: ArrayList<String> = ArrayList<String>()
                    var manual08List: ArrayList<String> = ArrayList<String>()
                    var img08List: ArrayList<String> = ArrayList<String>()
                    var manual09List: ArrayList<String> = ArrayList<String>()
                    var img09List: ArrayList<String> = ArrayList<String>()
                    var manual10List: ArrayList<String> = ArrayList<String>()
                    var img10List: ArrayList<String> = ArrayList<String>()
                    var manual11List: ArrayList<String> = ArrayList<String>()
                    var img11List: ArrayList<String> = ArrayList<String>()
                    var manual12List: ArrayList<String> = ArrayList<String>()
                    var img12List: ArrayList<String> = ArrayList<String>()
                    var manual13List: ArrayList<String> = ArrayList<String>()
                    var img13List: ArrayList<String> = ArrayList<String>()
                    var manual14List: ArrayList<String> = ArrayList<String>()
                    var img14List: ArrayList<String> = ArrayList<String>()
                    var manual15List: ArrayList<String> = ArrayList<String>()
                    var img15List: ArrayList<String> = ArrayList<String>()
                    var manual16List: ArrayList<String> = ArrayList<String>()
                    var img16List: ArrayList<String> = ArrayList<String>()
                    var manual17List: ArrayList<String> = ArrayList<String>()
                    var img17List: ArrayList<String> = ArrayList<String>()
                    var manual18List: ArrayList<String> = ArrayList<String>()
                    var img18List: ArrayList<String> = ArrayList<String>()
                    var manual19List: ArrayList<String> = ArrayList<String>()
                    var img19List: ArrayList<String> = ArrayList<String>()
                    var manual20List: ArrayList<String> = ArrayList<String>()
                    var img20List: ArrayList<String> = ArrayList<String>()

                    //여기서부턴 재료에 관한 것(여기도 더러움)
                    //recipe_ingredient에서 가져올 데이터들. recipe_ingredient나 recipe_manual이나, 둘 다 recipe_total.php에서 가져온 것들임
                    var idArray2: ArrayList<String> = ArrayList<String>() //idArray가 레시피 상세의 아이디면, 얘는 레시피 재료별 테이블의 id
                    var ingredient01: ArrayList<String> = ArrayList<String>()   //이 밑에 것들은 다 레시피에 들어갈 각기 재료들임
                    var ingredient02: ArrayList<String> = ArrayList<String>()   //db엔 어쩔 수 없이 null값도 들어가는데, 이는 백엔드 과정에서 처리 예정
                    var ingredient03: ArrayList<String> = ArrayList<String>()
                    var ingredient04: ArrayList<String> = ArrayList<String>()
                    var ingredient05: ArrayList<String> = ArrayList<String>()
                    var ingredient06: ArrayList<String> = ArrayList<String>()
                    var ingredient07: ArrayList<String> = ArrayList<String>()
                    var ingredient08: ArrayList<String> = ArrayList<String>()
                    var ingredient09: ArrayList<String> = ArrayList<String>()
                    var ingredient10: ArrayList<String> = ArrayList<String>()
                    var ingredient11: ArrayList<String> = ArrayList<String>()
                    var ingredient12: ArrayList<String> = ArrayList<String>()
                    var ingredient13: ArrayList<String> = ArrayList<String>()
                    var ingredient14: ArrayList<String> = ArrayList<String>()
                    var ingredient15: ArrayList<String> = ArrayList<String>()
                    var ingredient16: ArrayList<String> = ArrayList<String>()
                    var ingredient17: ArrayList<String> = ArrayList<String>()
                    var ingredient18: ArrayList<String> = ArrayList<String>()
                    var ingredient19: ArrayList<String> = ArrayList<String>()
                    var ingredient20: ArrayList<String> = ArrayList<String>()
                    var ingredient21: ArrayList<String> = ArrayList<String>()
                    var ingredient22: ArrayList<String> = ArrayList<String>()
                    var ingredient23: ArrayList<String> = ArrayList<String>()   //총 23개까지 있음. 솔직히 너무 많아서 코드가 더러워짐짐

                    var tempIngredientArray = mutableSetOf<String>()    //재료들을 어레이리스트로 담을 배열
                    var tempManualArray = mutableListOf<String>()        //레시피 별 단계들을 어레이 리스트로 담을 배열열
                    var tempImgArray = mutableListOf<String>()           //단계별 이미지를 어레이리스트로 담을 배열. null일 경우 no_image로 설정할 것임

                    for(i in 0 until arrayNum) {
                        idArray.add(jsonArray.getJSONObject(i).getInt("recipeId"))  //레시피 아이디
                        nameArray.add(jsonArray.getJSONObject(i).getString("recipeName"))   //레시피 이름
                        contentArray.add(jsonArray.getJSONObject(i).getString("recipeMethod")) //조리방법
                        categoryArray.add(jsonArray.getJSONObject(i).getString("recipeType"))   //음식 종류
                        imgSrcArray.add(jsonArray.getJSONObject(i).getString("recipeImgSrc"))//음식 메인 이미지
                        ingredientsArray.add(jsonArray.getJSONObject(i).getString("recipeIngredients"))
                        likeArray.add(jsonArray.getJSONObject(i).getInt("recipeLikeCnt"))
                        viewArray.add(jsonArray.getJSONObject(i).getInt("recipeViewCnt"))
                    }

                    RECIPELIST.clear()

                    //이 밑은 그냥 바로 위 for문처럼 다 가져오기 보다 그때그때 가져오려는 것임
                    for(i in 0 until arrayNum) {
                        for(j in 1 until 24) {
                            val temp = jsonArray2.getJSONObject(i).getString("ingredient$j")
                            if(!temp.equals("null")) {
                                tempIngredientArray.add(temp)       //null이 아닐 때까지 재료 추가
                            }
                            else
                                break;   //null이면 이미 재료 다 추가됐다는 것이므로 다음으로 이동
                        }
                        for(j in 1 until 21) {
                            val temp = jsonArray.getJSONObject(i).getString("Manual$j")
                            val temp2 = jsonArray.getJSONObject(i).getString("IMG$j")
                            if(!temp.equals("null")) {
                                tempManualArray.add(temp)       //null이 메뉴얼 추가
                                if(!temp2.equals("null"))
                                    tempImgArray.add(temp2)
                                else
                                    tempImgArray.add("no_image")//메뉴얼과 다르게 사진의 경우 없을수도 있으므로 no_image로 대체
                            }
                            else
                                break;  //null이면 메뉴얼도 다 추가됐다는 것이므로 다음으로 이동.
                        }

                        val tempIngredientList: ArrayList<String> = ArrayList<String>(tempIngredientArray)
                        val tempManualList: ArrayList<String> = ArrayList<String>(tempManualArray)
                        val tempImgList: ArrayList<String> = ArrayList<String>(tempImgArray)

                        RECIPELIST.add(
                            Recipe(idArray[i], nameArray[i], contentArray[i], categoryArray[i], imgSrcArray[i], ingredientsArray[i]
                                , tempIngredientList, tempManualList, tempImgList, likeArray[i], viewArray[i])
                        )
                        tempIngredientArray.clear() //얘네는 다음 번의 메뉴얼, 재료, 이미지들이 들어가야하므로 비워줘야함.
                        tempManualArray.clear()
                        tempImgArray.clear()
                    }

                    for(i in 0 until 20) {
                        println("레시피$i: "+RECIPELIST[i])
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
                "끓이기",
                "찌개",
                "이미지 url들어감",
                "김치, 두부, 고춧가루, 간장, 두부 등등",
                arrayListOf("조리 1단계", "조리 2단계", "조리 3단계", "조리 4단계"),
                arrayListOf("조리 1단계 이미지", "조리 2단계 이미지", "조리 3단계 이미지", "조리 4단계 이미지"),
                arrayListOf("김치", "두부", "고춧가루", "간장","두부","등등"),
                0,  //좋아요수
                0   //조회수
            ),
            Recipe(
                1,
                "김치찌개",
                "끓이기",
                "찌개",
                "이미지 url들어감",
                "김치, 두부, 고춧가루, 간장, 두부 등등",
                arrayListOf("조리 1단계", "조리 2단계", "조리 3단계", "조리 4단계"),
                arrayListOf("조리 1단계 이미지", "조리 2단계 이미지", "조리 3단계 이미지", "조리 4단계 이미지"),
                arrayListOf("김치", "두부", "고춧가루", "간장","두부","등등"),
                0,  //좋아요수
                0   //조회수
            )
        )
    }
}