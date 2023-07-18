package com.test.android74_memoapp

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android74_memoapp.databinding.CategoryMainInputDialogBinding
import com.test.android74_memoapp.databinding.FragmentCategoryMainBinding
import com.test.android74_memoapp.databinding.RowMainBinding


class CategoryMainFragment : Fragment() {

    lateinit var fragmentCategoryMainBinding: FragmentCategoryMainBinding
    lateinit var mainActivity: MainActivity

    lateinit var categoryDataList:MutableList<CategoryClass>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        fragmentCategoryMainBinding = FragmentCategoryMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 저장된 카테고리 정보를 가져온다.
        categoryDataList = CategoryDAO.selectAll(mainActivity)

        fragmentCategoryMainBinding.run{

            toolbarCategoryMain.run{
                title = "카테고리 목록"
                // 툴바의 메뉴 구성
                inflateMenu(R.menu.category_main_menu)
                // 툴바의 메뉴를 눌렀을 경우
                setOnMenuItemClickListener {

                    when(it.itemId){
                        R.id.categoryMainItem1 -> {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("카테고리 이름 입력")
                            val categoryMainInputDialogBinding = CategoryMainInputDialogBinding.inflate(layoutInflater)
                            builder.setView(categoryMainInputDialogBinding.root)
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                // 사용자가 입력한 카테고리 이름을 가져온다.
                                val newCategoryName = categoryMainInputDialogBinding.dialogInput.text.toString()
                                // 데이터 베이스에 저장한다.
                                val newCategoryClass = CategoryClass(0, newCategoryName)
                                CategoryDAO.insert(mainActivity, newCategoryClass)
                                // 데이터를 다시 가져와 리사이클러 뷰를 갱신한다.
                                categoryDataList = CategoryDAO.selectAll(mainActivity)
                                categoryRecyclerView.adapter?.notifyDataSetChanged()
                            }
                            builder.setNegativeButton("취소", null)

                            builder.show()
                            mainActivity.showSoftInput(categoryMainInputDialogBinding.dialogInput, 500)
                        }
                    }

                    false
                }

            }

            categoryRecyclerView.run{
                adapter = CategoryMainRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }
        }

        return fragmentCategoryMainBinding.root
    }

    inner class CategoryMainRecyclerViewAdapter : RecyclerView.Adapter<CategoryMainRecyclerViewAdapter.CategoryMainViewHolder>(){

        inner class CategoryMainViewHolder(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){

            var textViewRow:TextView

            init {
                textViewRow = rowMainBinding.textViewRow

                rowMainBinding.root.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
                    contextMenu.setHeaderTitle("${categoryDataList[adapterPosition].categoryName} 관리")
                    mainActivity.menuInflater.inflate(R.menu.category_main_context_menu, contextMenu)
                    // 첫번째 메뉴 (카테고리 삭제 수정)
                    contextMenu[0].setOnMenuItemClickListener {
                        val categoryMainInputDialogBinding = CategoryMainInputDialogBinding.inflate(layoutInflater)
                        val builder = AlertDialog.Builder(mainActivity)
                        builder.setTitle("카테고리 수정")
                        builder.setView(categoryMainInputDialogBinding.root)
                        categoryMainInputDialogBinding.dialogInput.setText(categoryDataList[adapterPosition].categoryName)
                        builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                            // 입력한 새로운 이름을 추출한다.
                            val newCategoryName = categoryMainInputDialogBinding.dialogInput.text.toString()
                            categoryDataList[adapterPosition].categoryName = newCategoryName
                            // 저장한다.
                            CategoryDAO.update(mainActivity, categoryDataList[adapterPosition])
                            // 다시 불러온다.
                            categoryDataList = CategoryDAO.selectAll(mainActivity)
                            fragmentCategoryMainBinding.categoryRecyclerView.adapter?.notifyDataSetChanged()
                        }
                        builder.setNegativeButton("취소", null)
                        builder.show()
                        false
                    }

                    // 두번째 메뉴 (카테고리 삭제)
                    contextMenu[1].setOnMenuItemClickListener {
                        // 해당 카테고리의 모든 메모를 삭제한다.
                        val deleteCategoryIdx = categoryDataList[adapterPosition].categoryIdx
                        MemoDAO.deleteMemoInCategory(mainActivity, deleteCategoryIdx)
                        // 현재 카테고리를 삭제한다.
                        CategoryDAO.delete(mainActivity, deleteCategoryIdx)
                        // 데이터를 다시 불러온다.
                        categoryDataList = CategoryDAO.selectAll(mainActivity)
                        // 리사이클러뷰를 갱신한다.
                        fragmentCategoryMainBinding.categoryRecyclerView.adapter?.notifyDataSetChanged()
                        false
                    }
                }

                rowMainBinding.root.setOnClickListener {
                    // 사용자가 선택한 항목의 categoryIdx를 담아준다.
                    val selectedCategoryIdx = categoryDataList[adapterPosition].categoryIdx
                    val newBundle = Bundle()
                    newBundle.putInt("category_idx", selectedCategoryIdx)
                    mainActivity.replaceFragment(MainActivity.MEMO_MAIN_FRAGMENT, true, true, newBundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMainViewHolder {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val categoryMainViewHolder = CategoryMainViewHolder(rowMainBinding)

            rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return categoryMainViewHolder
        }

        override fun getItemCount(): Int {
            return categoryDataList.size
        }

        override fun onBindViewHolder(holder: CategoryMainViewHolder, position: Int) {
            holder.textViewRow.text = categoryDataList[position].categoryName
        }
    }
}











