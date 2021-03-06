package com.example.Jachi3kki.fragment

import HorizontalItemDecorator
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.Jachi3kki.Adapter.MainFragmentAdapter
import com.example.Jachi3kki.Adapter.MainVitaminAdapter
import com.example.Jachi3kki.Class.Recipe
import com.example.Jachi3kki.Class.SelectedListItem
import com.example.Jachi3kki.activity.MainActivity
import com.example.Jachi3kki.R
import com.example.Jachi3kki.R.drawable
import com.example.Jachi3kki.R.layout
import com.example.Jachi3kki.OuterDB.recipeInfo
import com.google.android.material.navigation.NavigationView
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    class MainVitamin(val name: String, val img_src: String)

    var recipeList = arrayListOf<Recipe>()
    var vitaminList = arrayListOf<MainVitamin>()
    lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inputFormat = SimpleDateFormat("HH:mm:ss.SSS")
        val date = Date()
        inputFormat.format(date.time)

        return inflater.inflate(layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.instance.setSupportActionBar(toolbar)
        MainActivity.instance.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        MainActivity.instance.supportActionBar!!.setHomeAsUpIndicator(drawable.menu)

        navController = Navigation.findNavController(view)
        if (recipeList.isEmpty()) {
            addRecipeArray()
        }
        if (vitaminList.isEmpty()) {
            addVitaminArray()
        }
        toolbar.title = "??????3???"

        // recyclerView??? layout Manger ??????
        rv_data_list.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        rv_vitamin_list.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // ???????????? ?????? ????????? ??????
        rv_data_list.setHasFixedSize(true)
        rv_vitamin_list.setHasFixedSize(true)

        activity?.let { HorizontalItemDecorator(it, drawable.horizontal_line_divider, 0, 0) }?.let {
            rv_vitamin_list.addItemDecoration(
                it
            )
        }

        rv_data_list.adapter = activity?.let { it ->
            MainFragmentAdapter(recipeList, it) {
                Log.e("Index", it.name) //?????? ???????????? ??????????????? ?????????????????? ????????? ??????
            }
        }

        rv_vitamin_list.adapter = activity?.let { it ->
            MainVitaminAdapter(vitaminList, it) {
                Log.e("Index", it.name) //?????? ???????????? ??????????????? ?????????????????? ????????? ??????

                val selectedDataSet = arrayListOf<SelectedListItem>()
                selectedDataSet.add(SelectedListItem(it.name))
                navController.navigate(
                    R.id.action_mainFragment_to_ingredientFragment,
                    bundleOf("vitaminItem" to selectedDataSet)
                )
            }
        }

        navigationView.setNavigationItemSelectedListener(this)



        // ???????????? ??????????????? ??????
        val headerView = navigationView.getHeaderView(0)
        val loginBtn = headerView.findViewById<Button>(R.id.login_btn)
        loginBtn.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_loginFragment)
        }

        // ????????? ?????? ????????????
        if (MainActivity.id != null) {
            if (MainActivity.email != null) {
                headerView.findViewById<TextView>(R.id.email).text = "${MainActivity.email}"
            }
            headerView.findViewById<TextView>(R.id.nickName).text =
                "${MainActivity.profile?.nickname}"
            Glide.with(MainActivity.instance).load(
                MainActivity.profile?.thumbnailImageUrl)
                .into(headerView.findViewById<ImageView>(R.id.profileImage))

            headerView.findViewById<Button>(R.id.btn_logout).setVisibility(View.VISIBLE)
            headerView.findViewById<Button>(R.id.btn_logout).setEnabled(true)

            // ???????????? ?????? ?????? ???
            headerView.findViewById<Button>(R.id.btn_logout)!!.setOnClickListener {
                println("logout????????????")
                Toast.makeText(MainActivity.instance, "??????????????? ???????????? ???????????????.", Toast.LENGTH_SHORT)
                    .show()
                headerView.findViewById<TextView>(R.id.email).text = null
                headerView.findViewById<TextView>(R.id.nickName).text = null
                headerView.findViewById<ImageView>(R.id.profileImage).background = null
                headerView.findViewById<Button>(R.id.login_btn).setVisibility(View.VISIBLE)
                headerView.findViewById<Button>(R.id.login_btn).setEnabled(true)
                headerView.findViewById<Button>(R.id.btn_logout).setVisibility(View.INVISIBLE)
                headerView.findViewById<Button>(R.id.btn_logout).setEnabled(false)


                MainActivity.profile = null
                MainActivity.id = null
                MainActivity.email = null
                UserManagement.getInstance()
                    .requestLogout(object : LogoutResponseCallback() {
                        override fun onCompleteLogout() {
                            navController.navigate(R.id.action_global_mainFragment)
                        }
                    })
            }

            // ?????????
            headerView.findViewById<Button>(R.id.login_btn).setVisibility(View.GONE)
            headerView.findViewById<Button>(R.id.login_btn).setEnabled(false)
        }

    }

    private fun addVitaminArray() {
        vitaminList.add(MainVitamin("?????????A", "icon_a"))
        vitaminList.add(MainVitamin("?????????B", "icon_b"))
        vitaminList.add(MainVitamin("?????????C", "icon_c"))
        vitaminList.add(MainVitamin("?????????D", "icon_d"))
        vitaminList.add(MainVitamin("?????????E", "icon_e"))
        vitaminList.add(MainVitamin("?????????K", "icon_k"))
        vitaminList.add(MainVitamin("??????", "icon_ca"))
        vitaminList.add(MainVitamin("???", "icon_p"))
        vitaminList.add(MainVitamin("????????????", "icon_mg"))
        vitaminList.add(MainVitamin("??????", "icon_fe"))
        vitaminList.add(MainVitamin("??????", "icon_cu"))
        vitaminList.add(MainVitamin("??????", "icon_zn"))
        /*
        vitaminList.add(MainVitamin("?????????A", "http://118.67.132.138/vitamin_icon/icon_a.png"))
        vitaminList.add(MainVitamin("?????????B", "http://118.67.132.138/vitamin_icon/icon_b.png"))
        vitaminList.add(MainVitamin("?????????C", "http://118.67.132.138/vitamin_icon/icon_c.png"))
        vitaminList.add(MainVitamin("?????????D", "http://118.67.132.138/vitamin_icon/icon_d.png"))
        vitaminList.add(MainVitamin("?????????E", "http://118.67.132.138/vitamin_icon/icon_e.png"))
        vitaminList.add(MainVitamin("?????????K", "http://118.67.132.138/vitamin_icon/icon_k.png"))
        vitaminList.add(MainVitamin("??????", "http://118.67.132.138/vitamin_icon/icon_ca.png"))
        vitaminList.add(MainVitamin("???", "http://118.67.132.138/vitamin_icon/icon_p.png"))
        vitaminList.add(MainVitamin("????????????", "http://118.67.132.138/vitamin_icon/icon_mg.png"))
        vitaminList.add(MainVitamin("??????", "http://118.67.132.138/vitamin_icon/icon_fe.png"))
        vitaminList.add(MainVitamin("??????", "http://118.67.132.138/vitamin_icon/icon_cu.png"))
        vitaminList.add(MainVitamin("??????", "http://118.67.132.138/vitamin_icon/icon_zn.png"))
        */
    }

    private fun addRecipeArray() {  //?????? ????????? ????????????
        //????????? ???????????? ?????? ????????? ??? ??? 5?????? ?????? ??? ??? ????????? 2?????? ??????
        val indexList: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0)
        val tempList: MutableList<Int> = mutableListOf(-1, -1, -1, -1, -1)
        for (i in 0 until recipeInfo.RECIPELIST.size) {
            if (tempList[0] < recipeInfo.RECIPELIST[i].yesterdayView) {
                if (tempList[0] != -1) {
                    for (j in 4 downTo 1) {
                        indexList[j] = indexList[j - 1]
                        tempList[j] = tempList[j - 1]
                    }
                }
                indexList[0] = i
                tempList[0] = recipeInfo.RECIPELIST[i].yesterdayView
            } else if (tempList[1] < recipeInfo.RECIPELIST[i].yesterdayView) {
                if (tempList[1] != -1) {
                    for (j in 4 downTo 2) {
                        indexList[j] = indexList[j - 1]
                        tempList[j] = tempList[j - 1]
                    }
                }
                indexList[1] = i
                tempList[1] = recipeInfo.RECIPELIST[i].yesterdayView
            } else if (tempList[2] < recipeInfo.RECIPELIST[i].yesterdayView) {
                if (tempList[2] != -1) {
                    for (j in 4 downTo 3) {
                        indexList[j] = indexList[j - 1]
                        tempList[j] = tempList[j - 1]
                    }
                }
                indexList[2] = i
                tempList[2] = recipeInfo.RECIPELIST[i].yesterdayView
            } else if (tempList[3] < recipeInfo.RECIPELIST[i].yesterdayView) {
                if (tempList[3] != -1) {
                    indexList[4] = indexList[3]
                    tempList[4] = tempList[3]
                }
                indexList[3] = i
                tempList[3] = recipeInfo.RECIPELIST[i].yesterdayView
            } else if (tempList[4] < recipeInfo.RECIPELIST[i].yesterdayView) {
                indexList[4] = i
                tempList[4] = recipeInfo.RECIPELIST[i].yesterdayView
            }
        }

        val random = Random()
        val num1 = random.nextInt(5)
        var num2 = random.nextInt(5)
        if (num1 == num2) {
            while (num1 == num2) {
                num2 = random.nextInt(5)
            }
        }

        recipeList.add(recipeInfo.RECIPELIST[indexList[num1]])
        recipeList.add(recipeInfo.RECIPELIST[indexList[num2]])
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_bookmark -> {
                if (MainActivity.id == null) {
                    // ????????? ?????? ??????????????? ??????
                    alertLoginRequired()
                } else {
                    navController.navigate(R.id.action_mainFragment_to_bookmarkFragment)
                }
            }

        }
        return true
    }

    private fun alertLoginRequired() {
        AlertDialog.Builder(context)
            .setTitle("????????? ??????")
            .setMessage("???????????? ????????? ???????????????")
            .setPositiveButton("??????") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}