package com.example.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutine.adapter.MainAdapter
import com.example.coroutine.databinding.ActivityMainBinding
import com.example.coroutine.vm.MainViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration


class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var activityMainBinding: ActivityMainBinding
    }

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.run {
            dataList.observe(this@MainActivity) {
                runOnUiThread {
                    activityMainBinding.recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }

        activityMainBinding.run {
            recyclerView.run {
                mainViewModel.getDataList(this@MainActivity)

                adapter = MainAdapter(this@MainActivity, mainViewModel)
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

}

