package com.test.mvvmbasic

import com.test.mvvmbasic.adapter.MainAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.mvvmbasic.databinding.ActivityMainBinding
import com.test.mvvmbasic.databinding.RowBinding
import com.test.mvvmbasic.vm.TestViewModel

class MainActivity : AppCompatActivity() {

    lateinit var activityMainActivity: ActivityMainBinding
    lateinit var rowBinding: RowBinding

    lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainActivity.root)
        rowBinding= RowBinding.inflate(layoutInflater)

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

                adapter = MainAdapter(this@MainActivity,LayoutInflater.from(this@MainActivity),testViewModel)
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        testViewModel.getDataList(this)
    }
}









