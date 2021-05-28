package com.example.Jachi3kki.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.Jachi3kki.Class.SelectedListItem
import com.example.Jachi3kki.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), View.OnClickListener {
    lateinit var navController: NavController
    private val selectedDetailItem1 by lazy { arguments?.getParcelableArrayList<SelectedListItem>("detailItem1") }
    private val selectedDetailItem2 by lazy { arguments?.getParcelableArrayList<SelectedListItem>("detailItem2") }
    private val selectedDataSet1 = arrayListOf<SelectedListItem>()
    private val selectedDataSet2 = arrayListOf<SelectedListItem>()
    private val selectedMenuItem by lazy { arguments?.getParcelableArrayList<SelectedListItem>("item") }
    private val selectedAlignItem by lazy { arguments?.getParcelableArrayList<SelectedListItem>("alignmentItem") }
    private val selectedKeyword by lazy { arguments?.getString("keyword") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        if (!selectedDetailItem1.isNullOrEmpty()) {
            selectedDetailItem1?.forEach {
                when (it.data) {
                    "볶기" -> {
                        selectedDataSet1.add(SelectedListItem("볶기"))
                        checkBox1.isChecked = true
                    }
                    "찌기" -> {
                        selectedDataSet1.add(SelectedListItem("찌기"))
                        checkBox2.isChecked = true
                    }
                    "기타" -> {
                        selectedDataSet1.add(SelectedListItem("기타"))
                        checkBox3.isChecked = true
                    }
                    "튀기기" -> {
                        selectedDataSet1.add(SelectedListItem("튀기기"))
                        checkBox4.isChecked = true
                    }
                    "굽기" -> {
                        selectedDataSet1.add(SelectedListItem("굽기"))
                        checkBox5.isChecked = true
                    }
                    "끓이기" -> {
                        selectedDataSet1.add(SelectedListItem("끓이기"))
                        checkBox6.isChecked = true
                    }
                }
            }
        }

        if (!selectedDetailItem2.isNullOrEmpty()) {
            selectedDetailItem2?.forEach {
                when (it.data) {
                    "밥" -> {
                        selectedDataSet2.add(SelectedListItem("밥"))
                        checkBox7.isChecked = true
                    }
                    "반찬" -> {
                        selectedDataSet2.add(SelectedListItem("반찬"))
                        checkBox8.isChecked = true
                    }
                    "국&찌개" -> {
                        selectedDataSet2.add(SelectedListItem("국&찌개"))
                        checkBox9.isChecked = true
                    }
                    "후식" -> {
                        selectedDataSet2.add(SelectedListItem("후식"))
                        checkBox10.isChecked = true
                    }
                    "일품" -> {
                        selectedDataSet2.add(SelectedListItem("일품"))
                        checkBox11.isChecked = true
                    }
                    "기타" -> {
                        selectedDataSet2.add(SelectedListItem("기타"))
                        checkBox12.isChecked = true
                    }
                }
            }
        }


        var listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.checkBox1 -> selectedDataSet1.add(SelectedListItem("볶기"))
                    R.id.checkBox2 -> selectedDataSet1.add(SelectedListItem("찌기"))
                    R.id.checkBox3 -> selectedDataSet1.add(SelectedListItem("기타"))
                    R.id.checkBox4 -> selectedDataSet1.add(SelectedListItem("튀기기"))
                    R.id.checkBox5 -> selectedDataSet1.add(SelectedListItem("굽기"))
                    R.id.checkBox6 -> selectedDataSet1.add(SelectedListItem("끓이기"))
                    R.id.checkBox7 -> selectedDataSet2.add(SelectedListItem("밥"))
                    R.id.checkBox8 -> selectedDataSet2.add(SelectedListItem("반찬"))
                    R.id.checkBox9 -> selectedDataSet2.add(SelectedListItem("국&찌개"))
                    R.id.checkBox10 -> selectedDataSet2.add(SelectedListItem("후식"))
                    R.id.checkBox11 -> selectedDataSet2.add(SelectedListItem("일품"))
                    R.id.checkBox12 -> selectedDataSet2.add(SelectedListItem("기타"))
                }
            } else {
                when (buttonView.id) {
                    R.id.checkBox1 -> {
                        selectedDataSet1.remove(SelectedListItem("볶기"))
                        checkBox1.isChecked = false
                    }
                    R.id.checkBox2 -> {
                        selectedDataSet1.remove(SelectedListItem("찌기"))
                        checkBox2.isChecked = false
                    }
                    R.id.checkBox3 -> {
                        selectedDataSet1.remove(SelectedListItem("기타"))
                        checkBox3.isChecked = false
                    }
                    R.id.checkBox4 -> {
                        selectedDataSet1.remove(SelectedListItem("튀기기"))
                        checkBox4.isChecked = false
                    }
                    R.id.checkBox5 -> {
                        selectedDataSet1.remove(SelectedListItem("굽기"))
                        checkBox5.isChecked = false
                    }
                    R.id.checkBox6 -> {
                        selectedDataSet1.remove(SelectedListItem("끓이기"))
                        checkBox6.isChecked = false
                    }
                    R.id.checkBox7 -> {
                        selectedDataSet2.remove(SelectedListItem("밥"))
                        checkBox7.isChecked = false
                    }
                    R.id.checkBox8 -> {
                        selectedDataSet2.remove(SelectedListItem("반찬"))
                        checkBox8.isChecked = false
                    }
                    R.id.checkBox9 -> {
                        selectedDataSet2.remove(SelectedListItem("국&찌개"))
                        checkBox9.isChecked = false
                    }
                    R.id.checkBox10 -> {
                        selectedDataSet2.remove(SelectedListItem("후식"))
                        checkBox10.isChecked = false
                    }
                    R.id.checkBox11 -> {
                        selectedDataSet2.remove(SelectedListItem("일품"))
                        checkBox11.isChecked = false
                    }
                    R.id.checkBox12 -> {
                        selectedDataSet2.remove(SelectedListItem("기타"))
                        checkBox12.isChecked = false
                    }

                }
            }
        }
        checkBox1.setOnCheckedChangeListener(listener)
        checkBox2.setOnCheckedChangeListener(listener)
        checkBox3.setOnCheckedChangeListener(listener)
        checkBox4.setOnCheckedChangeListener(listener)
        checkBox5.setOnCheckedChangeListener(listener)
        checkBox6.setOnCheckedChangeListener(listener)
        checkBox7.setOnCheckedChangeListener(listener)
        checkBox8.setOnCheckedChangeListener(listener)
        checkBox9.setOnCheckedChangeListener(listener)
        checkBox10.setOnCheckedChangeListener(listener)
        checkBox11.setOnCheckedChangeListener(listener)
        checkBox12.setOnCheckedChangeListener(listener)

        detailClose.setOnClickListener(this)
        complete.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.detailClose -> navController.popBackStack()
            R.id.complete -> {
                if (selectedDataSet1.isEmpty()) {
                    selectedDataSet1.add(SelectedListItem("선택안함"))
                }
                if (selectedDataSet2.isEmpty()) {
                    selectedDataSet2.add(SelectedListItem("선택안함"))
                }
                navController.navigate(
                    R.id.action_detailFragment_to_recipeFragment,
                    bundleOf(
                        "detailItem1" to selectedDataSet1,
                        "detailItem2" to selectedDataSet2,
                        "item" to selectedMenuItem,
                        "alignmentItem" to selectedAlignItem,
                        "keyword" to selectedKeyword
                    )
                )
            }
        }
    }


}
