package com.test.mvvmbasic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.mvvmbasic.databinding.ActivityMainBinding
import com.test.mvvmbasic.databinding.RowBinding
import com.test.mvvmbasic.vm.TestViewModel

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        testViewModel = ViewModelProvider(this)[TestViewModel::class.java]
        testViewModel.run {
            testDataList.observe(this@MainActivity){
                activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
            }
        }

        activityMainBinding.run {
            buttonMainAdd.run {
                setOnClickListener {
                    val newIntent = Intent(this@MainActivity, AddActivity::class.java)
                    startActivity(newIntent)
                }
            }
            recyclerViewMain.run{
                testViewModel.getDataList(this@MainActivity)

                adapter = TestReyclerViewAdapter()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    inner class TestReyclerViewAdapter : RecyclerView.Adapter<TestReyclerViewAdapter.TestViewHolder>(){
        inner class TestViewHolder(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            var textViewRow1 : TextView
            var textViewRow2 : TextView

            init {
                textViewRow1 = rowBinding.textViewRow
                textViewRow2 = rowBinding.textViewRow2

                rowBinding.root.setOnClickListener {
                    testViewModel.getOneData(this@MainActivity,adapterPosition+1)
                    val newIntent = Intent(this@MainActivity, SubActivity::class.java)

                    newIntent.putExtra("dataResult1", testViewModel.testDataResult1.value)
                    newIntent.putExtra("dataResult2", testViewModel.testDataResult2.value)
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