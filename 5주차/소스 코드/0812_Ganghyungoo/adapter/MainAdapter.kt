package com.test.mvvmbasic.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.test.mvvmbasic.ShowDetailActivity
import com.test.mvvmbasic.databinding.RowBinding
import com.test.mvvmbasic.vm.TestViewModel

class MainAdapter(
    var comntext: Context,
    var layoutInflater: LayoutInflater,
    var testViewModel: TestViewModel,
) :
    RecyclerView.Adapter<MainAdapter.TestViewHolder>() {
    inner class TestViewHolder(rowBinding: RowBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {

        var textViewRow1: TextView
        var textViewRow2: TextView

        init {
            textViewRow1 = rowBinding.textView
            textViewRow2 = rowBinding.textView2
            rowBinding.root.setOnClickListener {
                val clickIdx = testViewModel.testDataList.value?.get(adapterPosition)?.testIdx
                val intent=Intent(comntext,ShowDetailActivity::class.java)
                intent.putExtra("idx",clickIdx)
                comntext.startActivity(intent)
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