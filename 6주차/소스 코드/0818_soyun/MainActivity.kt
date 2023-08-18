package com.test.study06_coroutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.mvvmbasic.model.TestDataClass
import com.test.mvvmbasic.vm.TestViewModel
import com.test.study06_coroutine.databinding.ActivityMainBinding
import com.test.study06_coroutine.databinding.RowBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var dataList : MutableList<TestDataClass>

    private lateinit var viewModel: TestViewModel
    private lateinit var adapter: TestRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        // ViewModel 초기화
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        adapter = TestRecyclerViewAdapter()


        activityMainBinding.run {
            // RecyclerView 설정
            recyclerViewMain.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewMain.adapter = adapter

        }

        // LiveData 관찰하여 데이터 변경에 대한 UI 업데이트
        viewModel.testDataList.observe(this) { testDataList ->
            setData(testDataList as MutableList<TestDataClass>)
        }

        // 데이터 가져오기 시작
        viewModel.fetchTestData()
    }

    inner class TestRecyclerViewAdapter : RecyclerView.Adapter<TestRecyclerViewAdapter.TestViewHolder>(){
        inner class TestViewHolder(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){

            var textViewRow1: TextView

            init{
                textViewRow1 = rowBinding.textView
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val testViewHolder = TestViewHolder(rowBinding)

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return testViewHolder
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
            holder.textViewRow1.text = dataList[position].title
        }

    }

    override fun onResume() {
        super.onResume()
    }

    // 데이터 변경을 위한 함수
    fun setData(newData: MutableList<TestDataClass>) {
        dataList = newData
        activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
    }
}