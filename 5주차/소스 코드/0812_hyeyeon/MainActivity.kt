package com.test.mvvmbasic

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.mvvmbasic.databinding.ActivityMainBinding
import com.test.mvvmbasic.databinding.RowBinding
import com.test.mvvmbasic.vm.TestViewModel

// 해당 프로젝트 내에서 리사이클러 뷰 항목 하나 클릭하는 경우 항목에 대한 설명이 있는 새액티비티가 나오는 과정을 MVVM패턴으로 구현
class MainActivity : AppCompatActivity() {

    lateinit var activityMainActivity: ActivityMainBinding

    lateinit var testViewModel: TestViewModel

    companion object {
        lateinit var mainActivity: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainActivity.root)

        mainActivity = this

        testViewModel = ViewModelProvider(this)[TestViewModel::class.java]
        testViewModel.run {
            testDataList.observe(this@MainActivity) {
                activityMainActivity.recyclerViewMain.adapter?.notifyDataSetChanged()
            }
        }

        activityMainActivity.run {
            buttonMainAdd.run {
                setOnClickListener {
                    val newIntent = Intent(this@MainActivity, AddActivity::class.java)
                    startActivity(newIntent)
                }
            }

            recyclerViewMain.run {
                testViewModel.getDataList(this@MainActivity)

                adapter = TestRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(
                    MaterialDividerItemDecoration(
                        this@MainActivity,
                        MaterialDividerItemDecoration.VERTICAL
                    )
                )
            }
        }
    }

    inner class TestRecyclerViewAdapter : RecyclerView.Adapter<TestRecyclerViewAdapter.TestViewHolder>() {
        inner class TestViewHolder(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root) {

            var textViewRow1: TextView
            var textViewRow2: TextView

            init {
                textViewRow1 = rowBinding.textView
                textViewRow2 = rowBinding.textView2

                rowBinding.root.setOnClickListener {
                    val newIntent = Intent(this@MainActivity, ResultActivity::class.java)

                    // 값을 가지고 있는 객체를 추출한다
                    val t1 = testViewModel.testDataList.value?.get(adapterPosition)

                    newIntent.putExtra("testIdx", t1?.testIdx)

                    startActivity(newIntent)
                }
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
            return testViewModel.testDataList.value?.size!!
        }

        override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
            holder.textViewRow1.text = testViewModel.testDataList.value?.get(position)?.testData1
            holder.textViewRow2.text = testViewModel.testDataList.value?.get(position)?.testData2
        }
    }

    override fun onResume() {
        super.onResume()

        testViewModel.getDataList(this)
    }
}









