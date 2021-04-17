package com.example.Jachi3kki.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.*
import androidx.recyclerview.widget.RecyclerView
import com.example.Jachi3kki.Category
import com.example.Jachi3kki.L
import com.example.Jachi3kki.R
import com.example.Jachi3kki.SelectedListItem
import com.example.Jachi3kki.databinding.CategoryGroupListItemBinding
import com.example.Jachi3kki.databinding.CategorySelectedListItemBinding
import com.example.Jachi3kki.databinding.FragmentIngredientBinding
import kotlinx.android.synthetic.main.fragment_ingredient.*

class IngredientFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentIngredientBinding

    //대분류 중분류 소분류 선택 리사이클러뷰의 어뎁터
    private lateinit var MainCategoryAdapter: ClassListAdapter
    private lateinit var MiddleCategoryAdapter: ClassListAdapter
    private lateinit var SubjectCategoryAdapter: ClassListAdapter
    private lateinit var SelectedCategoryAdapter: SelectedListAdapter

    //선택한 item의 중복방지와 순서유지를위해 hashmap을 사용
    // 카테고리에서 재료를 선택하면, 아래에 출력되는 아이템 Map
    private var selectedCategorys = LinkedHashMap<String, String>()

    //대분류 디폴트 데이터
    private val deFaultClass1 by lazy {
        arrayListOf<CategoryListItem>().also { list ->
            val class1 = Category.CLASS1

            class1.forEach {
                if (it.key == "육류") {
                    list.add(CategoryListItem(it.key, true))
                } else {
                    list.add(CategoryListItem(it.key, false))
                }
            }
        }
    }

    //중분류 디폴트데이터 육류
    private val deFaultClass2 by lazy {
        arrayListOf<CategoryListItem>().also { list ->
            val class2 = Category.CLASS2
            requireNotNull(class2["육류"]).src.forEach {
                list.add(CategoryListItem(it, false))
            }
        }
    }

    //layout을 inflate(xml의 내용을 실제 view 객체로 만듦)하는 단계. 여기서 view와 관련된 버튼 등을 초기화
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    //activity와 fragment의 view가 모두 생성된 상태로, view를 변경하는 작업이 가능한 단계
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btn_goRecipe.setOnClickListener {

            val selectedDataSet = arrayListOf<SelectedListItem>() //arraylistof로 아이템을 담아서
            selectedCategorys.entries.map {
                selectedDataSet.add(SelectedListItem(it.key))
            }
            navController.navigate(
                R.id.action_ingredientFragment_to_recipeFragment,
                bundleOf("item" to selectedDataSet)
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 대분류 adapter 생성
        MainCategoryAdapter = object : ClassListAdapter(requireContext()) {
            override fun onItemClick(index: Int, item: CategoryListItem) {
                //대분류에서 항목설정시 이벤트가 떨어진다.
                binding.tvMiddleCategory.text = item.data
                val class2 = Category.CLASS2

                MiddleCategoryAdapter.clearItems()
                SubjectCategoryAdapter.clearItems()

                // 전체 데이터에서 선택된 데이터를 찾아내어 값을 바꿔준다.
                MainCategoryAdapter.replaceAll(
                    // 선택된 데이터의 selected 상태를 true로 변경
                    changeSelectedState(
                        MainCategoryAdapter.getItemList(),
                        item.data
                    )
                )

                if (class2.containsKey(item.data)) {
                    val dataSet = arrayListOf<CategoryListItem>().also { list ->
                        class2[item.data]!!.src.forEach {
                            list.add(CategoryListItem(it, false))
                        }
                    }
                    MiddleCategoryAdapter.replaceAll(dataSet)
                }


            }
        }
        MiddleCategoryAdapter = object : ClassListAdapter(requireContext()) {
            override fun onItemClick(index: Int, item: CategoryListItem) {
                //중분류에서 항목설정시 이벤트가 떨어진다.
                binding.tvSubjectCategory.text = item.data

                val class3 = Category.CLASS3
                MiddleCategoryAdapter.replaceAll(
                    changeSelectedState(
                        MiddleCategoryAdapter.getItemList(),
                        item.data
                    )
                )
                if (class3.containsKey(item.data)) {

                    val dataSet = arrayListOf<CategoryListItem>().also { list ->
                        class3[item.data]!!.src.forEach {
                            list.add(CategoryListItem(it, false))
                        }
                    }
                    SubjectCategoryAdapter.replaceAll(dataSet)
                } else {
                    SubjectCategoryAdapter.clearItems()
                }
            }
        }
        SubjectCategoryAdapter = object : ClassListAdapter(requireContext()) {
            override fun onItemClick(index: Int, item: CategoryListItem) {
                //소분류에서 항목설정시 이벤트가 떨어진다.
                SubjectCategoryAdapter.replaceAll(
                    changeSelectedState(
                        SubjectCategoryAdapter.getItemList(),
                        item.data
                    )
                )

                if (!selectedCategorys.containsKey(item.data)) {
                    selectedCategorys[item.data] = item.data
                    val selectedDataSet = arrayListOf<CategoryListItem>()

                    selectedCategorys.entries.forEach {
                        selectedDataSet.add(CategoryListItem(it.key, false))
                    }
                    L.i("::::selectedDataSet " + selectedDataSet)
                    SelectedCategoryAdapter.replaceAll(selectedDataSet)
                }

            }
        }

        SelectedCategoryAdapter = object : SelectedListAdapter(requireContext()) {
            override fun onItemClick(index: Int, item: CategoryListItem) {
                //선택한 리스트뷰 클릭시 떨어지는 이벤트트
                L.i(":::::::::::::::선택한 선택한 카테고리 " + item.data)
                SelectedCategoryAdapter.removeItem(index)
                selectedCategorys.remove(item.data)
            }
        }

        binding.rvMainCategory.run {
            setHasFixedSize(true)
            adapter = MainCategoryAdapter
        }

        binding.rvMiddleCategory.run {
            setHasFixedSize(true)
            adapter = MiddleCategoryAdapter
        }

        binding.rvSubjectCategory.run {
            setHasFixedSize(true)
            adapter = SubjectCategoryAdapter
        }

        binding.rvSelectedCategory.run {
            setHasFixedSize(true)
            adapter = SelectedCategoryAdapter
        }

        //디폴트데이터를 초반에 설정해주세요.
        MainCategoryAdapter.replaceAll(deFaultClass1)
        MiddleCategoryAdapter.replaceAll(deFaultClass2)


        reLoadSelectedCategory()
    }


    private fun reLoadSelectedCategory(){
        //다시 카테고리 화면 돌아올시.. 선택한 메뉴들 ListUp 오른쪽에 back화살표 모양
        val selectedDataSet = arrayListOf<CategoryListItem>()
        selectedCategorys.entries.forEach {
            selectedDataSet.add(CategoryListItem(it.key, false))
        }
        L.i("::::selectedDataSet " + selectedDataSet)
        SelectedCategoryAdapter.replaceAll(selectedDataSet)
    }

    //선택한 list의 클릭상태를 변경하기위한 함수
    private fun changeSelectedState(
        categorys: List<CategoryListItem>?,
        data: String
    ): List<CategoryListItem>? {
        // map 함수는 각 원소를 원하는 형태로 변환하는 기능을 합니다.
        // 변환된 결과를 모아서 새 컬렉션을 만듭니다.
        return categorys?.map { category ->
            category.selected = category.data == data
            category
        }?.toList()
    }

    //리스트뷰 의 각 Item 클래스
    private data class CategoryListItem(val data: String, var selected: Boolean)

    // 대분류 중분류 소분류의 리사이클러뷰의 어뎁터를 만들기 위한 inner class
    // 대분류 중분류 소분류의 어뎁터가 이 클래스를 참조한다.
    private abstract inner class ClassListAdapter(private val context: Context) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        abstract fun onItemClick(index: Int, data: CategoryListItem)

        var items = mutableListOf<CategoryListItem>()

        // 만들어진 View가 없는 경우 xml 파일을 inflate해서 ViewHolder 생성
        // ViewHolder는 뷰를 보관하는 객체, 여러개 보관가능
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return CategoryGroupListItemViewHolder(
                CategoryGroupListItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }

        fun clearItems() {
            items.clear()
            notifyDataSetChanged()
        }

        fun replaceAll(items: List<CategoryListItem>?) {
            items?.let {
                this.items.run {
                    clear()
                    addAll(it)
                    notifyDataSetChanged()
                }
            }
        }

        fun getItemList(): List<CategoryListItem> {
            return items
        }

        override fun getItemCount(): Int = items.size

        // 만들어진 뷰와 실제 입력되는 각각의 데이터를 연결한다.
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is CategoryGroupListItemViewHolder) {
                val title = items[position].data
                val isSelected = items[position].selected
                holder.binding.root.text = title

                //클릭시 텍스트뷰의 배경과 글씨색상을 변경한다. isSelected true 시 클릭
                if (isSelected) {
                    holder.binding.root.setBackgroundColor(Color.parseColor("#e8b943"))
                    holder.binding.root.setTextColor(Color.parseColor("#fed966"))
                } else {
                    holder.binding.root.setBackgroundColor(Color.parseColor("#ffffff"))
                    holder.binding.root.setTextColor(Color.parseColor("#252525"))
                }
                holder.itemView.setOnClickListener {
                    onItemClick(position, items[position])
                }
            }
        }

        // Data binding
        // layout 이름을 category_group_list_item으로 정의했다면 DataBinding에 의해 생성된 데이터는 CategoryGroupListItemBinding.java 이다.
        // 실제 DataBinding 해야 할 객체는 CategoryGroupListItemBinding을 초기화 해야한다. root(view)를 뷰 홀더에 넘겨주자
        // https://thdev.tech/androiddev/2020/05/25/Android-RecyclerView-Adapter-Use-DataBinding/
        inner class CategoryGroupListItemViewHolder(val binding: CategoryGroupListItemBinding) :
            RecyclerView.ViewHolder(binding.root)
    }

    private abstract inner class SelectedListAdapter(private val context: Context) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        abstract fun onItemClick(index: Int, data: CategoryListItem)

        var items = mutableListOf<CategoryListItem>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return CategotySelectedListViewHolder(
                CategorySelectedListItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }

        fun replaceAll(items: List<CategoryListItem>?) {
            items?.let {
                this.items.run {
                    clear()
                    addAll(it)
                    notifyDataSetChanged()
                }
            }
        }

        fun removeItem(position: Int) {
            if (position < this.items.size) {
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, items.size)
            }
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is CategotySelectedListViewHolder) {
                val title = items[position].data
                holder.binding.tvSelectedTitle.text = title
                holder.itemView.setOnClickListener {
                    onItemClick(position, items[position])
                }
            }
        }

        inner class CategotySelectedListViewHolder(val binding: CategorySelectedListItemBinding) :
            RecyclerView.ViewHolder(binding.root)

    }
}
