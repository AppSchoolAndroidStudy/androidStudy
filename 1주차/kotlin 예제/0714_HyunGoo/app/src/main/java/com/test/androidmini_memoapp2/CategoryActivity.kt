package com.test.androidmini_memoapp2

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.androidmini_memoapp2.databinding.ActivityCategoryBinding
import com.test.androidmini_memoapp2.databinding.CategoryDialogBinding
import com.test.androidmini_memoapp2.databinding.CategoryItemBinding
import kotlin.concurrent.thread

class CategoryActivity : AppCompatActivity() {
    lateinit var activityCategoryBinding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCategoryBinding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(activityCategoryBinding.root)

        activityCategoryBinding.run {
            //툴바
            toolbar2.run {
                inflateMenu(R.menu.category_menu)
                //툴바의 추가 버튼 클릭시
                setOnMenuItemClickListener {
                    showDialog("카테고리 추가","추가")
                    false
                }
            }
            recyclerView.run {
                adapter = RecyclerAdapter()
                //최근 값이 맨위로 올라올 수 있도록
                var layoutManager1 = LinearLayoutManager(this@CategoryActivity)
                layoutManager1.reverseLayout = true
                layoutManager1.stackFromEnd = true
                layoutManager = layoutManager1
            }




        }
    }


    //다이얼로그 생성하는 메서드
    fun showDialog(titleName: String,useWay:String,position:Int?=null) {
        var adapter=activityCategoryBinding.recyclerView.adapter as RecyclerAdapter
        val dialogBinding = CategoryDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this@CategoryActivity)
        builder.setTitle(titleName)
        builder.setView(dialogBinding.root)
        if(useWay.equals("수정")){
            if(position!=null){
                dialogBinding.editTextText.setText(CategoryDAO.selectAllData(this@CategoryActivity)[position].name)
            }
        }
        dialogBinding.editTextText.requestFocus()
        //키보드 올리는 작업을 위한 쓰레드 실행
        thread {
            SystemClock.sleep(500)
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(dialogBinding.editTextText, 0)
        }
        //추가 클릭시 카테고리 추가
        builder.setPositiveButton(useWay) { dialogInterface: DialogInterface, i: Int ->
            if(useWay.equals("추가")){
                val categoryName = dialogBinding.editTextText.text.toString()
                val obj=Category(0,categoryName)
                CategoryDAO.insertCategory(this@CategoryActivity,obj)
            }else if(useWay.equals("수정")){
                if(position != null){
//                    Data.categoryList[position].name=dialogBinding.editTextText.text.toString()
                    val obj=CategoryDAO.selectData(this@CategoryActivity,CategoryDAO.selectAllData(this@CategoryActivity)[position].name)
                    obj.name=dialogBinding.editTextText.text.toString()
                    CategoryDAO.updateData(this,obj)
                }
                else{
                    Log.d("testt","수정 다이얼로그 오류.")
                }
                adapter.notifyDataSetChanged()
            }

        }
        builder.setNegativeButton("취소", null)
        builder.show()
    }

    inner class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(categoryItemBinding: CategoryItemBinding) :
            RecyclerView.ViewHolder(categoryItemBinding.root) {
            val categoryNameTextView: TextView

            init {
                categoryNameTextView = categoryItemBinding.categoryName
                //카테고리 컨텍스트 메뉴 생성
                categoryItemBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                    menu.setHeaderTitle(CategoryDAO.selectAllData(this@CategoryActivity)[adapterPosition].name)
                    menuInflater.inflate(R.menu.category_context, menu)

                    //카테고리 수정 컨텍스트 클릭 시 기능 구현(카테고리 객체와 해당 카테고리의 인덱스 번호도 보낸다.)
                    menu.getItem(0).setOnMenuItemClickListener {
                        showDialog("다이얼로그 수정","수정", position = adapterPosition)
                        false
                    }
                    //삭제 구현
                    menu.getItem(1).setOnMenuItemClickListener {
//                        Data.categoryList.removeAt(adapterPosition)
                        val name=CategoryDAO.selectAllData(this@CategoryActivity)[adapterPosition].name
                        CategoryDAO.deleteData(this@CategoryActivity,name)
                        this@RecyclerAdapter.notifyDataSetChanged()
                        false
                    }
                }

                //아이템 클릭 시 메모 보기 창으로 넘어감 (카테고리 테이블의 idx를 메모테이블이 참조 하고 있으므로 positoion을 넘겨서
                // 카테고리 테이블의 idx을 구해서 사용하려고한다.)
                categoryItemBinding.root.setOnClickListener {
                    val intent=Intent(this@CategoryActivity,MemoActivity::class.java)
                    val sendPosition=adapterPosition
                    intent.putExtra("position",sendPosition)
                    startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val categoryItemBinding = CategoryItemBinding.inflate(layoutInflater)
            val viewHolder = ViewHolder(categoryItemBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            categoryItemBinding.root.layoutParams = params

            return viewHolder
        }

        override fun getItemCount(): Int {
            return CategoryDAO.selectAllData(this@CategoryActivity).size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.categoryNameTextView.text = CategoryDAO.selectAllData(this@CategoryActivity)[position].name
        }
    }
}

