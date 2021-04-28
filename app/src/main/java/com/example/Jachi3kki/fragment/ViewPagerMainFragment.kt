package com.example.Jachi3kki.fragment

import android.graphics.Color
import android.graphics.Color.red
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.Jachi3kki.Adapter.ViewPagerAdapter
import com.example.Jachi3kki.R
import kotlinx.android.synthetic.main.viewpager_main.*

class ViewPagerMainFragment : Fragment() {
    //ViewPager 부모프래그먼트
    //자식 프래그먼트도 포함되어있다
    //어뎁터도 연결되어있다
    //어뎁터는 Adapter > ViewPagerAdapter.kt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.viewpager_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Adapter를 생성하면서 넘길 색상이 담긴 ArrayList<Int> 생성
        var bgColors = arrayListOf<Int>(
            R.color.red,
            R.color.orange,
            R.color.yellow,
            R.color.green,
            R.color.blue
        )

        // RecyclerView.Adapter<ViewHolder>()
        viewPager.adapter = ViewPagerAdapter(bgColors)
        // ViewPager의 Paging 방향은 Horizontal
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            // Paging 완료되면 호출
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("ViewPagerFragment", "Page ${position + 1}")
            }
        })
    }
}
