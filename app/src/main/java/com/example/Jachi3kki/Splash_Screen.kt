package com.example.Jachi3kki

import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.Jachi3kki.InternalDB.AppDatabase
import com.example.Jachi3kki.InternalDB.FridgeIngredient
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.Profile
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException
import kotlinx.android.synthetic.main.refrigerator.*
import kotlinx.android.synthetic.main.refrigerator.cart_image
import kotlinx.android.synthetic.main.splash_view.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_view);

        // 레시피 데이터
        recipeInfo.fetchJson_RecipeInfo()
        //냉장고 데이터 넣기
        var db: AppDatabase? = null
        db = AppDatabase.getInstance(this)
        var ingredientList = mutableListOf<FridgeIngredient>()
        ingredientList = db?.ingredientDAO()?.getAll()!!
        //비타민, 식재료 데이터 가져오기. ingredientList를 매개변수로 줘서 냉장고 재료까지 추가함
        Category.fetchJson_Ingredient(ingredientList)
        Category.fetchJson_vitamin()
        Category.fetchJson_vitaminToIngredient()

        Glide.with(this).load(R.raw.splash_final).into(splash_view)

        startLoading()
    }

    fun startLoading() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3400)
    }
}