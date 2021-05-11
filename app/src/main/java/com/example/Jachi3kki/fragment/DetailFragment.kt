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
    private val selectedMenuItem by lazy { arguments?.getParcelableArrayList<SelectedListItem>("item") }

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

                // 조리방법
                when (rg_method.checkedRadioButtonId) {
                    R.id.radio_1_1 -> selectedDataSet.add(SelectedListItem("볶기"))
                    R.id.radio_1_2 -> selectedDataSet.add(SelectedListItem("끓이기"))
                    R.id.radio_1_3 -> selectedDataSet.add(SelectedListItem("찌기"))
                    R.id.radio_1_4 -> selectedDataSet.add(SelectedListItem("굽기"))
                    R.id.radio_1_5 -> selectedDataSet.add(SelectedListItem("튀기기"))
                    R.id.radio_1_6 -> selectedDataSet.add(SelectedListItem("기타"))
                    else -> selectedDataSet.add(SelectedListItem("선택안함"))
                }

                // 음식종류
                when (rg_kind.checkedRadioButtonId) {
                    R.id.radio_2_1 -> selectedDataSet.add(SelectedListItem("밥"))
                    R.id.radio_2_2 -> selectedDataSet.add(SelectedListItem("반찬"))
                    R.id.radio_2_3 -> selectedDataSet.add(SelectedListItem("국&찌개"))
                    R.id.radio_2_4 -> selectedDataSet.add(SelectedListItem("기타"))
                    R.id.radio_2_5 -> selectedDataSet.add(SelectedListItem("후식"))
                    R.id.radio_2_6 -> selectedDataSet.add(SelectedListItem("일품"))
                    else -> selectedDataSet.add(SelectedListItem("선택안함"))
                }

                navController.navigate(
                    R.id.action_detailFragment_to_recipeFragment,
                    bundleOf("detailItem" to selectedDataSet, "item" to selectedMenuItem)
                )

            }
        }
    }
}