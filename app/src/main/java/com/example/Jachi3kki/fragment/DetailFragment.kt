package com.example.Jachi3kki.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.Jachi3kki.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), View.OnClickListener {
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        close.setOnClickListener(this)
        completion.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.close -> navController.popBackStack()
            R.id.completion ->{
                navController.navigate(R.id.action_alignmentFragment_to_recipeFragment)
            }
        }
    }
}