package com.test.android74_memoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android74_memoapp.databinding.FragmentMemoMainBinding
import com.test.android74_memoapp.databinding.RowMainBinding


class MemoMainFragment : Fragment() {

    lateinit var fragmentMemoMainBinding: FragmentMemoMainBinding
    lateinit var mainActivity: MainActivity
    lateinit var memoDataList:MutableList<MemoClass>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMemoMainBinding = FragmentMemoMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 카테고리 인덱스를 추출한다.
        val categoryIdx = arguments?.getInt("category_idx")

        // 현재 카테고리에 대한 메모 데이터를 가져온다.
        memoDataList = MemoDAO.selectAll(mainActivity, categoryIdx!!)

        // 카테고리 정보를 가져온다.
        val categoryClass = CategoryDAO.selectOne(mainActivity, categoryIdx!!)

        fragmentMemoMainBinding.run{
            toolbarMemoMain.run{
                title = categoryClass?.categoryName
                inflateMenu(R.menu.category_main_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.categoryMainItem1 -> {
                            // 선택한 카테고리 번호를 번들에 담아 전달한다.
                            val newBundle = Bundle()
                            newBundle.putInt("category_idx", categoryIdx)
                            mainActivity.replaceFragment(MainActivity.MEMO_ADD_FRAGMENT, true, true, newBundle)
                        }
                    }
                    false
                }
                // 백버튼
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MEMO_MAIN_FRAGMENT)
                }
            }

            recyclerViewMemoMain.run{
                adapter = MemoMainRecyclerAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }
        }

        return fragmentMemoMainBinding.root
    }

    inner class MemoMainRecyclerAdapter : RecyclerView.Adapter<MemoMainRecyclerAdapter.MemoMainViewHolder>(){

        inner class MemoMainViewHolder(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            var textViewRow:TextView

            init {
                textViewRow = rowMainBinding.textViewRow

                rowMainBinding.root.setOnClickListener {
                    // 사용자가 선택한 메모의 인덱스값을 전달한다.
                    val selectedMemoIdx = memoDataList[adapterPosition].memoIdx
                    val newBundle = Bundle()
                    newBundle.putInt("memo_idx", selectedMemoIdx)
                    mainActivity.replaceFragment(MainActivity.MEMO_READ_FRAGMENT, true, true, newBundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoMainViewHolder {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val memoMainViewHolder = MemoMainViewHolder(rowMainBinding)

            rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return memoMainViewHolder
        }

        override fun getItemCount(): Int {
            return memoDataList.size
        }

        override fun onBindViewHolder(holder: MemoMainViewHolder, position: Int) {
            holder.textViewRow.text = memoDataList[position].memoSubject
        }
    }
}












