package com.example.coroutine.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coroutine.MainActivity
import com.example.coroutine.ResultActivity
import com.example.coroutine.databinding.RowBinding
import com.example.coroutine.vm.MainViewModel

class MainAdapter(var context: Context, var mainViewModel: MainViewModel) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root) {
        val imageViewRow: ImageView
        val textViewRow: TextView

        init {
            imageViewRow = rowBinding.imageViewRow
            textViewRow = rowBinding.textViewRow

            rowBinding.root.setOnClickListener {
                val newIntent = Intent((context as MainActivity), ResultActivity::class.java)
                newIntent.putExtra("rowIdx", adapterPosition)
                context.startActivity(newIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowBinding = RowBinding.inflate((context as MainActivity).layoutInflater)
        val viewHolder = ViewHolder(rowBinding)

        rowBinding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return viewHolder
    }

    override fun getItemCount(): Int {
        return mainViewModel.dataList?.value?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context as MainActivity).load(mainViewModel.dataList.value?.get(position)?.image).into(holder.imageViewRow)
        holder.textViewRow.text = mainViewModel.dataList.value?.get(position)?.title.toString()
    }
}
