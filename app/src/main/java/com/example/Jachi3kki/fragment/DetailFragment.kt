package com.example.Jachi3kki.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.Jachi3kki.Class.SelectedListItem
import com.example.Jachi3kki.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), View.OnClickListener {
    lateinit var navController: NavController
    private val selectedMenuItem  by lazy { arguments?.getParcelableArrayList<SelectedListItem>("item")}

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

        detailClose.setOnClickListener(this)
        completion.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.detailClose -> navController.popBackStack()
            R.id.completion -> {
                val selectedDataSet = arrayListOf<SelectedListItem>()

                // 요리수준
                if (rg_all.checkedRadioButtonId == R.id.all_button) {
                    selectedDataSet.add(SelectedListItem("요리수준전체"))
                } else {
                    when (rg_level.checkedRadioButtonId) {
                        R.id.radio_top -> selectedDataSet.add(SelectedListItem("요리수준상"))
                        R.id.radio_middle -> selectedDataSet.add(SelectedListItem("요리수준중"))
                        R.id.radio_bottom -> selectedDataSet.add(SelectedListItem("요리수준하"))
                        else -> selectedDataSet.add(SelectedListItem("선택안함"))
                    }
                }

                // 요리종류
                when (rg_kind.checkedRadioButtonId) {
                    R.id.radioButton8 -> selectedDataSet.add(SelectedListItem("요리종류한식"))
                    R.id.radioButton9 -> selectedDataSet.add(SelectedListItem("요리종류중식"))
                    R.id.radioButton10 -> selectedDataSet.add(SelectedListItem("요리종류양식"))
                    R.id.radioButton11 -> selectedDataSet.add(SelectedListItem("요리종류일식"))
                    else -> selectedDataSet.add(SelectedListItem("선택안함"))
                }

                // 포함된 재료로만 검색
                if(rg_optionOnly.checkedRadioButtonId == R.id.option_only){
                    selectedDataSet.add(SelectedListItem("포함된 재료로만 검색"))
                }

                navController.navigate(R.id.action_detailFragment_to_recipeFragment,
                    bundleOf("detailItem" to selectedDataSet, "item" to selectedMenuItem))

            }
        }
    }
}