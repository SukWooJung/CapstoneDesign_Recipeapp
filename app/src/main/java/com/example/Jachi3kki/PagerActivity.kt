package com.example.Jachi3kki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.Jachi3kki.R
import com.example.Jachi3kki.fragment.ViewPagerMainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class PagerActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)
        val recipeNum = intent.getIntExtra("recipeNum",0)
        //Fragment 연결해주는 코드
        //해당 코드가 없으면 빈화면이 나옴
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentFrame, ViewPagerMainFragment(recipeNum)).commit()
    }
}