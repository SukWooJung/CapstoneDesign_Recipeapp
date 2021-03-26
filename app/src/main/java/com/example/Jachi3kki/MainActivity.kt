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

        // val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
        navController = nav_host_fragment.findNavController()

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when (p0.itemId) {
            R.id.menu_home -> {
                Toast.makeText(this,"home clicked",Toast.LENGTH_SHORT).show()
                transaction.replace(R.id.frame_layout, MainFragment(), "home")
            }
            R.id.menu_ingredient -> {
                Toast.makeText(this,"ingredient clicked",Toast.LENGTH_SHORT).show()
                val fragmentIngredient = IngredientFragment()
                transaction.replace(R.id.frame_layout, fragmentIngredient, "ingredient")
            }
            R.id.menu_vitamin -> {
                Toast.makeText(this,"vitamin clicked",Toast.LENGTH_SHORT).show()
                val fragmentVitamin = VitaminFragment()
                transaction.replace(R.id.frame_layout, fragmentVitamin, "vitamin")
            }
            R.id.menu_bookmark -> {
                Toast.makeText(this,"bookmark clicked",Toast.LENGTH_SHORT).show()
                val fragmentBookmark = BookmarkFragment()
                transaction.replace(R.id.frame_layout, fragmentBookmark, "bookmark")
            }
            R.id.menu_fridge -> {
                Toast.makeText(this,"fridge clicked",Toast.LENGTH_SHORT).show()
                val fragmentFridge = FridgeFragment()
                transaction.replace(R.id.frame_layout, fragmentFridge, "fridge")
            }
        }
        // transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
        return true
    }

}