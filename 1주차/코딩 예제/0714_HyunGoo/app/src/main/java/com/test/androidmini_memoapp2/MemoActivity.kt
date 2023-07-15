package com.test.androidmini_memoapp2

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.androidmini_memoapp2.databinding.ActivityMemoBinding
import com.test.androidmini_memoapp2.databinding.CategoryItemBinding
import com.test.androidmini_memoapp2.databinding.MemoItemBinding
import com.test.androidmini_memoapp2.databinding.SwitchItemBinding

class MemoActivity : AppCompatActivity() {
    lateinit var activityMemoBinding: ActivityMemoBinding
    val checkedItemNameList = mutableListOf<String>()
    var recyclerViewAllCheckBox= mutableListOf<CheckBox>()
    lateinit var checkboxMenu:CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMemoBinding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(activityMemoBinding.root)
        //카테고리에 해당하는 메모를 구하기 위해서는 카테고리의idx는 필수적 이기에 position을 받아와서 필요한 것을 뽑아낸다.
        val index = intent.getIntExtra("position", -1)
        val categoryName = CategoryDAO.selectAllData(this)[index].name
        val categoryId = CategoryDAO.selectAllData(this)[index].idx

        activityMemoBinding.run {
            toolbar3.run {
                setTitle("$categoryName")
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.memo_menu)
                val checkBoxItem = activityMemoBinding.toolbar3.menu.findItem(R.id.check_menu_all)
                checkboxMenu = checkBoxItem.actionView?.findViewById<CheckBox>(R.id.checkBox)!!

                checkboxMenu?.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        for(itemCheckBox in recyclerViewAllCheckBox){
                            itemCheckBox.isChecked=true
                        }
//                        menu.getItem(1).setIcon(R.drawable.trash)
                        setTitle("메모 전체 선택")
                    } else {
                        for(itemCheckBox in recyclerViewAllCheckBox){
                            itemCheckBox.isChecked=false
                        }
                        setTitle("$categoryName")
//                        menu.getItem(1).setIcon(R.drawable.ic_menu_add)
                    }
                }

                //백버튼 띄우기
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                // 백 버튼의 아이콘 색상을 변경한다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                }
                // 백 버튼을 누르면 동작하는 리스너
                setNavigationOnClickListener {
                    if (checkboxMenu.isChecked) {
                        checkboxMenu.isChecked = false
                    } else {
                        finish()
                    }
                }
                //툴바의 추가 버튼 클릭시
                setOnMenuItemClickListener {
                    if (checkboxMenu.isChecked||0<checkedItemNameList.size) {
                        for(name in checkedItemNameList){
                            MemoDAO.deleteData(this@MemoActivity,categoryId,name)
                        }
                        checkedItemNameList.clear()
                        for(checkedBox in recyclerViewAllCheckBox){
                            checkedBox.isChecked=false
                        }
                        recyclerViewAllCheckBox.clear()
                        val adapter=activityMemoBinding.recyclerView.adapter as RecyclerAdapter
                        adapter.notifyDataSetChanged()
                        checkboxMenu.isChecked = false
                        true
                    } else {
                        val intent = Intent(this@MemoActivity, AddMemoActivity::class.java)
                        intent.putExtra("categoryId", categoryId)
                        startActivity(intent)
                        false
                    }

                }

            }
            recyclerView.run {
                adapter = RecyclerAdapter(categoryId)
                //최근 값이 맨위로 올라올 수 있도록
                var layoutManager1 = LinearLayoutManager(this@MemoActivity)
                layoutManager1.reverseLayout = true
                layoutManager1.stackFromEnd = true
                layoutManager = layoutManager1
            }
        }
    }


    inner class RecyclerAdapter(var id: Int) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(memoItemBinding: MemoItemBinding) :
            RecyclerView.ViewHolder(memoItemBinding.root) {
            val memoNameTextView: TextView
            val recyclerCheckBox:CheckBox

            init {
                memoNameTextView = memoItemBinding.memoName
                recyclerCheckBox= memoItemBinding.memoCheckBox
                //메모 아이템 하나 클릭 구현
                memoItemBinding.root.setOnClickListener {
                    val intent = Intent(this@MemoActivity, ShowMemoActivity::class.java)
                    //메모 상세 정보를 얻기위해 메모의idx)
                    intent.putExtra("id", id)
                    intent.putExtra("position", adapterPosition)
                    startActivity(intent)
                }
                memoItemBinding.memoCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        checkedItemNameList.add(
                            MemoDAO.selectAllData(
                                this@MemoActivity,
                                id
                            )[adapterPosition].memoName
                        )
                        Log.d("please", "${checkedItemNameList.size}")
                    } else {
                        if(checkedItemNameList.size!=0){
                            checkedItemNameList.remove(
                                MemoDAO.selectAllData(
                                    this@MemoActivity,
                                    id
                                )[adapterPosition].memoName
                            )
                            Log.d("please", "${checkedItemNameList.size}")
                        }
                    }
                    if (checkedItemNameList.size > 0) {
                        activityMemoBinding.toolbar3.run {
                            menu.findItem(R.id.add_memo).setIcon(R.drawable.trash)
                        }
                    } else {
                        activityMemoBinding.toolbar3.run {
                            menu.findItem(R.id.add_memo).setIcon(R.drawable.ic_menu_add)
                        }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val memoItemBinding = MemoItemBinding.inflate(layoutInflater)
            val viewHolder = ViewHolder(memoItemBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            memoItemBinding.root.layoutParams = params

            return viewHolder
        }

        override fun getItemCount(): Int {
            return MemoDAO.selectAllData(this@MemoActivity, id).size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.memoNameTextView.text =
                MemoDAO.selectAllData(this@MemoActivity, id)[position].memoName
            recyclerViewAllCheckBox.add(holder.recyclerCheckBox)
        }
    }

    override fun onRestart() {
        super.onRestart()
        val adapter = activityMemoBinding.recyclerView.adapter as RecyclerAdapter
        adapter.notifyDataSetChanged()
    }
}