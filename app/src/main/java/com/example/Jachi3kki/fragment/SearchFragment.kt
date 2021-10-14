package com.example.Jachi3kki.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.Jachi3kki.activity.MainActivity
import com.example.Jachi3kki.R
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private lateinit var navController: NavController
    private var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        et_search.setOnEditorActionListener { _, actionId, _ ->
            // 검색 완료 시 처리하기
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // 검색창에서 포커스 언로크ㅡ
                et_search.clearFocus()

                // 키보드 숨기기
                val imm =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.hideSoftInputFromWindow(et_search.windowToken, 0)

                // 입력한 검색어로 검색
                search(et_search.text.toString())
                true
            } else {
                false
            }
        }
    }

    //동근님 화이팅....
    private fun search(Search: String) {
        Toast.makeText(context, "$Search 와 관련된 검색 결과", Toast.LENGTH_SHORT).show()
        navController.navigate(
            R.id.action_searchFragment_to_recipeFragment,
            bundleOf("keyword" to Search)
        )
    }
}