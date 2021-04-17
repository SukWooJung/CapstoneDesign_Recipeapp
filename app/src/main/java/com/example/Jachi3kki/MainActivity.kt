package com.example.Jachi3kki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Category.fetchJson()
        // val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
        navController = nav_host_fragment.findNavController()

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        // val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when (p0.itemId) {
            R.id.menu_home -> {
                Toast.makeText(this,"home clicked",Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.mainFragment)

            }
            R.id.menu_ingredient -> {
                Toast.makeText(this,"ingredient clicked",Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.ingredientFragment)
            }
            R.id.menu_vitamin -> {
                Toast.makeText(this,"vitamin clicked",Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.vitaminFragment)
            }
            R.id.menu_bookmark -> {
                Toast.makeText(this,"bookmark clicked",Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.bookmarkFragment)
            }
            R.id.menu_fridge -> {
                Toast.makeText(this,"fridge clicked",Toast.LENGTH_SHORT).show()
               navController.navigate(R.id.fridgeFragment)
            }
        }
        // transaction.addToBackStack(null) => 백스택에 저장해서 그 위에 프레그먼트를 덮는 형식 (카드 레이아웃과 비슷)
        // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) => 프레그먼트 넘길 때 잔상이 남는다.
        // transaction.commit()
        return true
    }

}