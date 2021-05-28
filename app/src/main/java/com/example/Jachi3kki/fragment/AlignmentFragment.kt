package com.example.Jachi3kki.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.Jachi3kki.Class.SelectedListItem
import com.example.Jachi3kki.R
import kotlinx.android.synthetic.main.fragment_alignment.*

class AlignmentFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    private val selectedKeyword by lazy { arguments?.getString("keyword") }
    private val selectedMenuItem by lazy { arguments?.getParcelableArrayList<SelectedListItem>("item") }
    private val selectedDetailItem1 by lazy { arguments?.getParcelableArrayList<SelectedListItem>("detailItem1") }
    private val selectedDetailItem2 by lazy { arguments?.getParcelableArrayList<SelectedListItem>("detailItem2") }
    private val fromInt by lazy { arguments?.getInt("fromInt") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_alignment, container, false)
        // return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        close.setOnClickListener(this)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.close -> navController.popBackStack()
            R.id.button -> {
                val selectedDataSet = arrayListOf<SelectedListItem>()
                when (rg_recommand.checkedRadioButtonId) {
                    R.id.likeCntSort -> selectedDataSet.add(SelectedListItem("추천높은순"))
                    R.id.likeCntSortReverse -> selectedDataSet.add(SelectedListItem("추천낮은순"))
                    else -> selectedDataSet.add(SelectedListItem("선택안함"))
                }

                when (rg_view.checkedRadioButtonId) {
                    R.id.viewCntSort -> selectedDataSet.add(SelectedListItem("조회수높은순"))
                    R.id.viewCntSortReverse -> selectedDataSet.add(SelectedListItem("조회수낮은순"))
                    else -> selectedDataSet.add(SelectedListItem("선택안함"))
                }
                if (fromInt == 1) {
                    navController.navigate(R.id.action_alignmentFragment_to_bookmarkFragment, bundleOf("alignmentItem" to selectedDataSet))
                } else if (fromInt == 0) {
                    navController.navigate(
                        R.id.action_alignmentFragment_to_recipeFragment,
                        bundleOf(
                            "alignmentItem" to selectedDataSet,
                            "item" to selectedMenuItem,
                            "detailItem1" to selectedDetailItem1,
                            "detailItem2" to selectedDetailItem2,
                            "keyword" to selectedKeyword
                        )
                    )
                }
            }
        }
    }

}