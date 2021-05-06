package com.example.Jachi3kki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import com.example.Jachi3kki.InternalDB.AppDatabase
import com.example.Jachi3kki.InternalDB.FridgeIngredient
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        lateinit var instance: MainActivity
    }

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        instance = this
        // val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
        navController = nav_host_fragment.findNavController()

        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        // val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when (p0.itemId) {
            R.id.menu_home -> {
                Toast.makeText(this, "home clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_global_mainFragment)

            }
            R.id.menu_ingredient -> {
                Toast.makeText(this, "ingredient clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_global_ingredientFragment)
            }
            R.id.menu_vitamin -> {
                Toast.makeText(this, "vitamin clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_global_vitaminFragment)
            }
            R.id.menu_bookmark -> {
                Toast.makeText(this, "bookmark clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_global_bookmarkFragment)
            }
            R.id.menu_fridge -> {
                Toast.makeText(this, "fridge clicked", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_global_fridgeFragment)
            }
        }
        // transaction.addToBackStack(null) => 백스택에 저장해서 그 위에 프레그먼트를 덮는 형식 (카드 레이아웃과 비슷)
        // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) => 프레그먼트 넘길 때 잔상이 남는다.
        // transaction.commit()
        return true
    }

    var backPressedTime: Long = 0
    private final var FINISH_INTERVAL_TIME: Long = 2000

    override fun onBackPressed() {
        if (!navController.popBackStack()) { // currentBackStackEntry가 비어있으면 false 리턴. 따라서, 비어있는 경우
            val currentTime = System.currentTimeMillis()
            val intervalTime = currentTime - backPressedTime
            if (!(0 > intervalTime || FINISH_INTERVAL_TIME < intervalTime)) {
                finishAffinity()
                System.runFinalization()
                System.exit(0)
            } else {
                backPressedTime = currentTime
                Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // 홈화면으로 온 경우
        println("test: " + navController.currentDestination)
        val temp = navController.currentDestination
        // val tag1: Fragment? = supportFragmentManager.findFragmentById(R.id.mainFragment)
        val tag1 = navController.graph.get(R.id.mainFragment)
        if (temp == tag1) {
            val bnv = findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView
            updateBottomMenu(bnv)
        }
    }


    private fun updateBottomMenu(navigation: BottomNavigationView) {
        navigation.menu.findItem(R.id.menu_home).isChecked = true
    }

}