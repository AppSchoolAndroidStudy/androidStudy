package com.woojugoing.mvvmbasic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.woojugoing.mvvmbasic.databinding.ActivityMainBinding
import com.woojugoing.mvvmbasic.databinding.RowMainBinding
import com.woojugoing.mvvmbasic.vm.ViewModel1

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel1: ViewModel1

    companion object{
        lateinit var mainActivity: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        viewModel1 = ViewModelProvider(this)[ViewModel1::class.java]
        viewModel1.dataList.observe(this@MainActivity){ activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged() }
        setContentView(activityMainBinding.root)
        mainActivity = this
        activityMainBinding.run {
            buttonMainAdd.run {
                setOnClickListener {
                    val newIntent = Intent(this@MainActivity, AddActivity::class.java)
                    startActivity(newIntent)
                }
            }

            recyclerViewMain.run {
                viewModel1.getDataList(this@MainActivity)
                adapter = RecyclerViewAdapter()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    inner class RecyclerViewAdapter(): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(rowMainBinding: RowMainBinding): RecyclerView.ViewHolder(rowMainBinding.root){
            val textViewRow1: TextView
            val textViewRow2: TextView
            init {
                textViewRow1 = rowMainBinding.textViewRow1
                textViewRow2 = rowMainBinding.textViewRow2
                rowMainBinding.root.setOnClickListener {
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    val list = viewModel1.dataList.value?.get(adapterPosition)
                    intent.putExtra("idx", list?.idx)
                    startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val viewHolder = ViewHolder(rowMainBinding)

            rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolder
        }

        override fun getItemCount(): Int {
            return viewModel1.dataList.value?.size!!
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textViewRow1.text = viewModel1.dataList.value?.get(position)?.data1
            holder.textViewRow2.text = viewModel1.dataList.value?.get(position)?.data2
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel1.getDataList(this)
    }
}