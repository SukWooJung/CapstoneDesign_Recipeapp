package com.example.Jachi3kki.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.Class.SelectedListItem
import com.example.Jachi3kki.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_alignment.*
import kotlinx.android.synthetic.main.fragment_main.*

class AlignmentFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    private val selectedMenuItem  by lazy { arguments?.getParcelableArrayList<SelectedListItem>("item")}
    private val selectedDetailItem by lazy { arguments?.getParcelableArrayList<SelectedListItem>("detailItem") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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

        when(v?.id){
            R.id.close -> navController.popBackStack()
            R.id.button ->{
                val selectedDataSet = arrayListOf<SelectedListItem>()
                when(rg_recommand.checkedRadioButtonId){
                    R.id.likeCntSort->selectedDataSet.add(SelectedListItem("추천높은순"))
                    R.id.likeCntSortReverse->selectedDataSet.add(SelectedListItem("추천낮은순"))
                    else -> selectedDataSet.add(SelectedListItem("선택안함"))
                }

                when(rg_view.checkedRadioButtonId){
                    R.id.viewCntSort->selectedDataSet.add(SelectedListItem("조회수높은순"))
                    R.id.viewCntSortReverse->selectedDataSet.add(SelectedListItem("조회수낮은순"))
                    else -> selectedDataSet.add(SelectedListItem("선택안함"))
                }

                navController.navigate(R.id.action_alignmentFragment_to_recipeFragment,
                    bundleOf("alignmentItem" to selectedDataSet, "item" to selectedMenuItem, "detailItem" to selectedDetailItem))
            }
        }
    }

}